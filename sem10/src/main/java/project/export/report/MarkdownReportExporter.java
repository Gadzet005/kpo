
package project.export.report;

import project.report.Report;

import java.io.IOException;
import java.io.Writer;

public class MarkdownReportExporter implements ReportExporter {
    @Override
    public void export(Report report, Writer writer) throws IOException {
        writer.write("# " + report.title() + "\n\n");
        writer.write(report.content());
        writer.flush();
    }
}