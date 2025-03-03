package project.export.report;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.report.Report;

import java.io.IOException;
import java.io.Writer;

public class JsonReportExporter implements ReportExporter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void export(Report report, Writer writer) throws IOException {
        objectMapper.writeValue(writer, report);
    }
}