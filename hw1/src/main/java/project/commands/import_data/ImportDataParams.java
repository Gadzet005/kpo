package project.commands.import_data;

import project.consts.ImportExportTarget;
import project.consts.ImportExportType;

public record ImportDataParams(String data, ImportExportTarget target,
        ImportExportType type) {
}
