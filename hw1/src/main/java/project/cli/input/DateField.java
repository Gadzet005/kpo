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

    @Builder
    public DateField(String name, String description, String dateFormatString,
            boolean isNullable) {
        super(name, description, null, null, isNullable);
        if (description == null) {
            this.description = "date";
        }

        this.dateFormatString = dateFormatString == null ? DEFAULT_FORMAT_STRING
                : dateFormatString;
        this.dateFormat = new SimpleDateFormat(this.dateFormatString);
    }

    @Override
    public ValidationResult<Date> validate(String str) {
        try {
            return ValidationResult.valid(dateFormat.parse(str));
        } catch (Exception e) {
            return ValidationResult.invalid(
                    "Invalid date format. Expected: " + dateFormatString);
        }
    }
}
