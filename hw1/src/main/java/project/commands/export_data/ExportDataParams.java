package project.commands.export_data;

import java.io.Writer;

import project.consts.ImportExportTarget;
import project.consts.ImportExportType;

public record ExportDataParams(Writer writer, ImportExportTarget target,
        ImportExportType type) {
}
