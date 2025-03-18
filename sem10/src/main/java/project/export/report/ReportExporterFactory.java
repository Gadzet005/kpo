package project.export.report;

import project.enums.ReportFormat;
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