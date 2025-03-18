package project.commands.export_data;

import project.consts.ImportExportTarget;
import project.consts.ImportExportType;

public record ExportDataParams(ImportExportTarget target,
        ImportExportType type) {
}
