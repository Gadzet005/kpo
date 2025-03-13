package project.cli.functions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.cli.input.ChoiceField;
import project.cli.input.StringField;
import project.commands.CommandWithTimer;
import project.commands.import_data.ImportDataCommand;
import project.commands.import_data.ImportDataParams;
import project.consts.ImportExportTarget;
import project.consts.ImportExportType;
import project.utils.EnumUtils;
import project.utils.FileUtils;

@Component
public class ImportData implements CLIFunction {
    private Scanner reader;
    private CommandWithTimer<Integer, ImportDataParams> importDataCommand;

    @Autowired
    public ImportData(Scanner reader, ImportDataCommand importDataCommand) {
        this.reader = reader;
        this.importDataCommand = new CommandWithTimer<>(importDataCommand);
    }

    private void importFile(String filePath) {
        System.out.println("Importing data from file: " + filePath);

        Path path = Paths.get(filePath);
        String data;
        try {
            data = Files.readString(path);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        var fileBaseName = FileUtils.getBaseName(filePath);
        var fileExt = FileUtils.getExtension(filePath);

        ImportExportType type;
        try {
            type = ImportExportType.valueOf(fileExt.toUpperCase());
            System.out.println("Automatically detected import type as "
                    + type.toString().toLowerCase());
        } catch (IllegalArgumentException e) {
            var typeRaw = (String) ChoiceField.builder().name("Import type")
                    .choices(EnumUtils.getNames(ImportExportType.class))
                    .choiceFormat(choise -> choise.toString().toLowerCase())
                    .build().execute(reader);
            type = ImportExportType.valueOf(typeRaw);
        }

        ImportExportTarget target;
        try {
            target = ImportExportTarget.valueOf(fileBaseName.toUpperCase());
            System.out.println("Automatically detected import target as "
                    + target.toString().toLowerCase());
        } catch (IllegalArgumentException e) {
            var targetRaw = (String) ChoiceField.builder().name("Import target")
                    .choices(EnumUtils.getNames(ImportExportTarget.class))
                    .choiceFormat(choise -> choise.toString().toLowerCase())
                    .build().execute(reader);
            target = ImportExportTarget.valueOf(targetRaw);
        }

        try {
            var result = importDataCommand
                    .execute(new ImportDataParams(data, target, type));
            System.out.println(
                    "Successfully imported " + result.wrapped() + " objects");
            System.out.println("Done in " + result.duration() + " ms");
        } catch (Exception e) {
            System.out.println("Error importing data: " + e.getMessage());
        }
    }

    @Override
    public void execute() {
        var raw = StringField.builder().name("Files to import").defaultValue(
                "test_data/bank_accounts.csv, test_data/categories.csv, test_data/operations.csv")
                .build().execute(reader);
        var files = raw.split(",");
        for (var file : files) {
            importFile(file.trim());
        }
    }
}
