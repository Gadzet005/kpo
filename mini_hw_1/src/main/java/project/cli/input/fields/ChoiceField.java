package project.cli.input.fields;

import lombok.Builder;
import project.cli.input.fields.base.InputField;
import project.cli.input.fields.base.ValidationResult;

public class ChoiceField extends InputField<Object> {
    private Object[] choices;

    @Builder
    public ChoiceField(String name, String description, Object[] choices) {
        super(name, description, choices.length > 0 ? choices[0] : null);
        this.choices = choices;
        if (this.description == null) {
            this.description = "enter number";
        }
    }

    @Override
    protected String getLabel() {
        var label = super.getLabel() + "\n";
        for (int i = 0; i < choices.length; i++) {
            label += (i + 1) + ". " + choices[i] + "\n";
        }
        return label;
    }

    @Override
    protected ValidationResult<Object> handleInput(String input) {
        try {
            var choiceIndex = Integer.parseInt(input) - 1;
            if (choiceIndex < 0 || choiceIndex >= choices.length) {
                return ValidationResult.invalid("Invalid choice");
            }
            return ValidationResult.valid(choices[choiceIndex]);
        } catch (NumberFormatException e) {
            return ValidationResult.invalid("Invalid choice number");
        }
    }
}
