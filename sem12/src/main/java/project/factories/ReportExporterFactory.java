package project.factories;

import project.enums.ReportFormat;
import project.export.reports.ReportExporter;
import project.export.reports.impl.JsonReportExporter;
import project.export.reports.impl.MarkdownReportExporter;
import org.springframework.stereotype.Component;

@Component
public class ReportExporterFactory {
    public ReportExporter create(ReportFormat format) {
        return switch (format) {
        case JSON -> new JsonReportExporter();
        case MARKDOWN -> new MarkdownReportExporter();
        default -> throw new IllegalArgumentException(
                "Unsupported format: " + format);
        };
    }
}
