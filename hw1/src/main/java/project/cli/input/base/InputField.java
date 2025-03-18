package project.cli.input.base;

import java.util.Scanner;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class InputField<R> {
    protected String name = "Field";
    protected String description = null;
    protected R defaultValue = null;
    protected String defaultLabel = null;
    protected boolean isNullable = false;

    /**
     * Validates the given input string and returns a ValidationResult.
     *
     * @param str the input string to be validated
     * @return a ValidationResult containing the validation outcome and the
     *         parsed result if valid
     */
    public abstract ValidationResult<R> validate(String str);

    /**
     * Executes the input field and returns the parsed result.
     *
     * @param reader the Scanner to read input from the user
     * @return parsed result
     */
    public R execute(Scanner reader) {
        while (true) {
            System.out.print(getLabel());

            var input = reader.nextLine();
            if (input.isEmpty() && (isNullable || defaultValue != null)) {
                return defaultValue;
            }
            var validationResult = validate(input);

            if (validationResult.isValid()) {
                return validationResult.result();
            }
            System.out.println(validationResult.errorMessage());
        }
    }

    protected String getDefaultValueLabel() {
        if (defaultLabel != null) {
            return defaultLabel;
        }
        return String.valueOf(defaultValue);
    }

    protected String getLabel() {
        var builder = new StringBuilder();
        builder.append(name);
        if (description != null) {
            builder.append(" (");
            builder.append(description);
            builder.append(")");
        }
        if (defaultValue != null) {
            builder.append(" [default: ");
            builder.append(getDefaultValueLabel());
            builder.append("]");
        }
        builder.append(": ");
        return builder.toString();
    }
}