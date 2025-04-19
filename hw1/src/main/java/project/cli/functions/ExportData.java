package project.cli.functions;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

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

    private static StringField exportInput = StringField.builder()
            .name("Export directory").description("path to the directory")
            .build();
    private static ChoiceField<Object> exportTargetInput = ChoiceField.builder()
            .name("export target").choices(ExportData.exportTargetChoices())
            .choiceFormat(choise -> choise.toString().toLowerCase()).build();
    private static ChoiceField<Object> exportTypeInput = ChoiceField.builder()
            .name("export type")
            .choices(EnumUtils.getNames(ImportExportType.class))
            .choiceFormat(choise -> choise.toString().toLowerCase()).build();

    private Scanner reader;
    private CommandWithTimer<String, ExportDataParams> exportDataCommand;

    @Autowired
    public ExportData(Scanner reader, ExportDataCommand exportDataCommand) {
        this.exportDataCommand = new CommandWithTimer<>(exportDataCommand);
        this.reader = reader;
    }

    private static String[] exportTargetChoices() {
        return Stream
                .concat(List.of(ALL).stream(),
                        Arrays.stream(
                                EnumUtils.getNames(ImportExportTarget.class)))
                .toArray(String[]::new);
    }

    private void export(Path dirPath, ImportExportTarget target,
            ImportExportType type) {
        var fileName = target.toString().toLowerCase() + "."
                + type.toString().toLowerCase();
        var filePath = dirPath.resolve(fileName);

        System.out.println("Exporting data to file: " + filePath.toString());
        try (var writer = new FileWriter(filePath.toString())) {
            var result = exportDataCommand
                    .execute(new ExportDataParams(target, type));

            var data = result.wrapped();
            writer.write(data);

            System.out.println("Data was successfully exported.");
            System.out.println("Done in " + result.duration() + " ms");
        } catch (IOException e) {
            System.out.println("Error creating export file: " + e.getMessage());
        } catch (CommandError e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void execute() {
        var exportDirectory = exportInput.execute(reader);
        Path dirPath = Paths.get(exportDirectory);
        if (!Files.isDirectory(dirPath)) {
            System.out.println("Invalid export directory.");
            return;
        }

        var exportTargetRaw = (String) exportTargetInput.execute(reader);
        var exportTypeRaw = (String) exportTypeInput.execute(reader);

        var exportType = ImportExportType.valueOf(exportTypeRaw);
        if (exportTargetRaw.equals(ALL)) {
            for (var target : ImportExportTarget.class.getEnumConstants()) {
                export(dirPath, target, exportType);
            }
        } else {
            var targetType = ImportExportTarget.valueOf(exportTargetRaw);
            export(dirPath, targetType, exportType);
        }
    }
}
