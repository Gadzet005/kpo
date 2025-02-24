package project.cli.input.fields.base;

public record ValidationResult<TResult>(boolean isValid, TResult result,
        String errorMessage) {
    public static <TResult> ValidationResult<TResult> valid(TResult result) {
        return new ValidationResult<TResult>(true, result, null);
    }

    public static <TResult> ValidationResult<TResult> invalid(
            String errorMessage) {
        return new ValidationResult<TResult>(false, null, errorMessage);
    }
}
