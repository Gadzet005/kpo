package project.report;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import project.domains.Customer;

public class ReportBuilder {
    static private DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE;
    StringBuilder content = new StringBuilder();

    public ReportBuilder addCustomers(List<Customer> customers) {
        content.append("\nПокупатели:");
        customers.forEach(
                customer -> content.append(String.format("\n- %s", customer)));
        content.append("\n\n");

        return this;
    }

    public ReportBuilder addOperation(String operation) {
        content.append(String.format("Операция: %s", operation));
        content.append(System.lineSeparator());
        return this;
    }

    public Report build() {
        return new Report(
                String.format("Отчет за %s",
                        ZonedDateTime.now().format(DATE_TIME_FORMATTER)),
                content.toString());
    }
}
