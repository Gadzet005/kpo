package project.cli.functions;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.cli.input.ChoiceField;
import project.cli.input.StringField;
import project.commands.CommandError;
import project.commands.CommandWithTimer;
import project.commands.export_data.ExportDataCommand;
import project.commands.export_data.ExportDataParams;
import project.consts.ImportExportTarget;
import project.consts.ImportExportType;
import project.utils.EnumUtils;

@Component
public class ExportData implements CLIFunction {
    private static final String ALL = "all";
    private Scanner reader;
    private CommandWithTimer<Integer, ExportDataParams> exportDataCommand;

    @Autowired
    public ExportData(Scanner reader, ExportDataCommand exportDataCommand) {
        this.exportDataCommand = new CommandWithTimer<>(exportDataCommand);
        this.reader = reader;
    }

    private void export(Path dirPath, ImportExportTarget target,
            ImportExportType type) {
        var fileName = target.toString().toLowerCase() + "."
                + type.toString().toLowerCase();
        var filePath = dirPath.resolve(fileName);

        System.out.println("Exporting data to file: " + filePath.toString());
        try (var writer = new FileWriter(filePath.toString())) {
            var result = exportDataCommand
                    .execute(new ExportDataParams(writer, target, type));
            System.out.println(
                    "Successfully exported " + result.wrapped() + " objects.");
            System.out.println("Done in " + result.duration() + " ms");
        } catch (IOException e) {
            System.out.println("Error creating export file: " + e.getMessage());
        } catch (CommandError e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void execute() {
        var exportDirectory = StringField.builder().name("Export directory")
                .description("path to the directory").build().execute(reader);

        Path dirPath = Paths.get(exportDirectory);
        if (!Files.isDirectory(dirPath)) {
            System.out.println("Invalid export directory.");
            return;
        }

        var targetChoices = new ArrayList<>(Arrays.asList(ALL));
        targetChoices.addAll(
                Arrays.asList(EnumUtils.getNames(ImportExportTarget.class)));
        var exportTargetRaw = (String) ChoiceField.builder()
                .name("export target").choices(targetChoices.toArray())
                .choiceFormat(choise -> choise.toString().toLowerCase()).build()
                .execute(reader);

        var exportTypeRaw = (String) ChoiceField.builder().name("export type")
                .choices(EnumUtils.getNames(ImportExportType.class))
                .choiceFormat(choise -> choise.toString().toLowerCase()).build()
                .execute(reader);

        var exportTargets = new ArrayList<ImportExportTarget>();
        if (exportTargetRaw.equals(ALL)) {
            exportTargets.addAll(
                    Arrays.asList(ImportExportTarget.class.getEnumConstants()));
        } else {
            exportTargets.add(ImportExportTarget.valueOf(exportTargetRaw));
        }

        var exportType = ImportExportType.valueOf(exportTypeRaw);

        for (var exportTarget : exportTargets) {
            export(dirPath, exportTarget, exportType);
        }
    }
}
