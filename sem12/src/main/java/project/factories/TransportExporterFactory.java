package project.factories;

import project.enums.ReportFormat;
import project.export.reports.ReportExporter;
import project.export.reports.impl.JsonReportExporter;
import project.export.reports.impl.MarkdownReportExporter;
import project.export.transport.TransportExporter;
import project.export.transport.impl.CsvTransportExporter;
import project.export.transport.impl.XmlTransportExporter;
import org.springframework.stereotype.Component;

@Component
public class TransportExporterFactory {
    public TransportExporter create(ReportFormat format) {
        return switch (format) {
        case XML -> new XmlTransportExporter();
        case CSV -> new CsvTransportExporter();
        default -> throw new IllegalArgumentException(
                "Unsupported format: " + format);
        };
    }
}
