package project.cli.input.base;

public record ValidationResult<R>(boolean isValid, R result,
        String errorMessage) {
    public static <R> ValidationResult<R> valid(R result) {
        return new ValidationResult<>(true, result, null);
    }

    public static <R> ValidationResult<R> invalid(String errorMessage) {
        return new ValidationResult<>(false, null, errorMessage);
    }
}
