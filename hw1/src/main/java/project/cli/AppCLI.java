package project.cli;

import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import project.cli.functions.*;

record CLIFunctionRecord(String name, CLIFunction executor) {
}

@Component
public class AppCLI {
    private Scanner reader;
    private ApplicationContext ctx;
    private ArrayList<CLIFunctionRecord> functions = new ArrayList<>();

    @Autowired
    public AppCLI(ApplicationContext context) {
        this.ctx = context;
        this.reader = ctx.getBean(Scanner.class);
    }

    private <T extends CLIFunction> void registerFunction(String name,
            Class<T> function) {
        functions.add(new CLIFunctionRecord(name, ctx.getBean(function)));
    }

    @PostConstruct
    public void initFunctions() {
        registerFunction("Show bank account statistics",
                ShowBankAccountStatistics.class);
        registerFunction("Show bank account operations",
                ShowBankAccountOperations.class);
        registerFunction("Fix bank account", FixBankAccount.class);
        registerFunction("Import data", ImportData.class);
        registerFunction("Export data", ExportData.class);
    }

    private void help() {
        System.out.println("Available functions:");
        for (int i = 0; i < functions.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, functions.get(i).name());
        }
        System.out.println("Enter the number of the function to execute.");
    }

    private void callCLIFunction(int idx) {
        if (idx < 0 || idx >= functions.size()) {
            System.out.println("Invalid function number.");
            return;
        }

        var function = functions.get(idx);
        try {
            function.executor().execute();
        } catch (Exception e) {
            System.out
                    .println("An unexpected error occurred: " + e.getMessage());
            return;
        }
    }

    public void start() {
        System.out.println("Welcome to the HSE bank!");
        System.out.println("h -> show awailable functions.");
        System.out.println("q -> quit the system.");

        while (true) {
            System.out.print(">>> ");
            String input = reader.nextLine();

            if ("q".equalsIgnoreCase(input)) {
                System.out.println("Goodbye!");
                break;
            } else if ("h".equalsIgnoreCase(input)) {
                help();
            } else {
                try {
                    var functionIdx = Integer.parseInt(input) - 1;
                    callCLIFunction(functionIdx);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }
        }
    }
}
