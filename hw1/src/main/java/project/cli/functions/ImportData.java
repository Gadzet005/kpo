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
    private static StringField importFilesInput = StringField.builder()
            .name("Files to import").defaultValue(
                    // TODO make this better
                    "test_data/bank_accounts.csv, test_data/categories.csv, test_data/operations.csv")
            .defaultLabel("test_data/*.csv").build();
    private static ChoiceField<Object> importTypeInput = ChoiceField.builder()
            .name("Import type")
            .choices(EnumUtils.getNames(ImportExportType.class))
            .choiceFormat(choise -> choise.toString().toLowerCase()).build();
    private static ChoiceField<Object> importTargetInput = ChoiceField.builder()
            .name("Import target")
            .choices(EnumUtils.getNames(ImportExportTarget.class))
            .choiceFormat(choise -> choise.toString().toLowerCase()).build();

    private Scanner reader;
    private CommandWithTimer<Integer, ImportDataParams> importDataCommand;

    @Autowired
    public ImportData(Scanner reader, ImportDataCommand importDataCommand) {
        this.reader = reader;
        this.importDataCommand = new CommandWithTimer<>(importDataCommand);
    }

    private ImportExportType importType(String raw) {
        try {
            var type = ImportExportType.valueOf(raw);
            System.out.println("Automatically detected import type as "
                    + type.toString().toLowerCase());
            return type;
        } catch (IllegalArgumentException e) {
            var typeRaw = (String) importTypeInput.execute(reader);
            return ImportExportType.valueOf(typeRaw);
        }
    }

    private ImportExportTarget importTarget(String raw) {
        try {
            var target = ImportExportTarget.valueOf(raw);
            System.out.println("Automatically detected import target as "
                    + target.toString().toLowerCase());
            return target;
        } catch (IllegalArgumentException e) {
            var targetRaw = (String) importTargetInput.execute(reader);
            return ImportExportTarget.valueOf(targetRaw);
        }
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
        ImportExportType type = importType(fileExt.toUpperCase());
        ImportExportTarget target = importTarget(fileBaseName.toUpperCase());

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
        var raw = importFilesInput.execute(reader);
        var files = raw.split(",");
        for (var file : files) {
            importFile(file.trim());
        }
    }
}
