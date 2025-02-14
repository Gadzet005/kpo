package project.cli.input.fields.base;

import java.util.Scanner;

import lombok.AllArgsConstructor;

/**
 * Represents an input field that can be executed to obtain a result.
 *
 * @param <TResult> The type of result that this input field produces.
 */
@AllArgsConstructor
public abstract class InputField<TResult> {
    protected String name = "Field";
    protected String description = null;
    protected TResult defaultValue = null;

    protected abstract ValidationResult<TResult> handleInput(String input);

    public TResult execute(Scanner reader) {
        while (true) {
            System.out.print(getLabel());

            var input = reader.nextLine();
            if (input == "" && defaultValue != null) {
                return defaultValue;
            }
            var validationResult = handleInput(input);

            if (validationResult.isValid()) {
                return validationResult.result();
            }
            System.out.println(validationResult.errorMessage());
        }
    }

    protected String getLabel() {
        String label = name;
        if (description != null) {
            label += " (" + description + ")";
        }
        if (defaultValue != null) {
            label += " [default: " + defaultValue + "]";
        }
        return label + ": ";
    }
}