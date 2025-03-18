package project.export.report;

import java.io.IOException;
import java.io.Writer;
import project.report.Report;

public interface ReportExporter {
    void export(Report report, Writer writer) throws IOException;
}