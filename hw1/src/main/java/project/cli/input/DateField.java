package project.cli.input;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Builder;
import project.cli.input.base.InputField;
import project.cli.input.base.ValidationResult;

public class DateField extends InputField<Date> {
    private static final String DEFAULT_FORMAT_STRING = "dd-MM-yyyy";
    private DateFormat dateFormat;
    private String dateFormatString;
    private boolean nullable;

    @Builder
    public DateField(String name, String description, String dateFormatString,
            Boolean nullable) {
        super(name, description, null);
        if (description == null) {
            this.description = "date";
        }

        this.dateFormatString = dateFormatString == null ? DEFAULT_FORMAT_STRING
                : dateFormatString;
        this.dateFormat = new SimpleDateFormat(this.dateFormatString);
        this.nullable = nullable != null && nullable;
    }

    @Override
    protected ValidationResult<Date> handleInput(String input) {
        if (input.equals("") && nullable) {
            return ValidationResult.valid(null);
        }
        try {
            if (input.equalsIgnoreCase("today")) {
                return ValidationResult.valid(new Date());
            }
            return ValidationResult.valid(dateFormat.parse(input));
        } catch (Exception e) {
            return ValidationResult.invalid(
                    "Invalid date format. Expected: " + dateFormatString);
        }
    }
}
