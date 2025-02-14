package project;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import project.cli.AppCLI;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);
        var reader = context.getBean(Scanner.class);

        var cliFunctions = context.getBean(AppCLI.class).getFunctions();
        var functionNames = cliFunctions.getFunctionNames();

        System.out.println("Welcome to the zoo accounting system.");
        System.out.println("h -> show awailable functions.");
        System.out.println("q -> quit the system.");

        while (true) {
            System.out.print(">>> ");
            String input = reader.nextLine();

            if ("q".equalsIgnoreCase(input)) {
                break;
            } else if ("h".equalsIgnoreCase(input)) {
                System.out.println("Available functions:");
                for (int i = 0; i < functionNames.length; i++) {
                    System.out.printf("%d. %s%n", i + 1, functionNames[i]);
                }
                System.out.println(
                        "Enter the number of the function to execute.");
            } else {
                try {
                    var functionIdx = Integer.parseInt(input) - 1;
                    if (functionIdx < 0
                            || functionIdx >= functionNames.length) {
                        System.out.println("Invalid function number.");
                        continue;
                    }

                    var function = cliFunctions.getFunction(functionIdx);

                    if (function != null) {
                        function.execute();
                    } else {
                        System.out.println("Function not found.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }
        }
    }
}
