package project.cli.input.base;

import java.util.Scanner;

import lombok.AllArgsConstructor;

/**
 * Represents an input field that can be executed to obtain a result.
 *
 * @param <TResult> The type of result that this input field produces.
 */
@AllArgsConstructor
public abstract class InputField<R> {
    protected String name = "Field";
    protected String description = null;
    protected R defaultValue = null;

    protected abstract ValidationResult<R> handleInput(String input);

    public R execute(Scanner reader) {
        while (true) {
            System.out.print(getLabel());

            var input = reader.nextLine();
            if (input.isEmpty() && defaultValue != null) {
                return defaultValue;
            }
            var validationResult = handleInput(input);

            if (validationResult.isValid()) {
                return validationResult.result();
            }
            System.out.println(validationResult.errorMessage());
        }
    }

    protected String getDefaultValueLabel() {
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