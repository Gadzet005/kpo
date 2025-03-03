package project.params;

/**
 * Represents an empty parameter set for an engine. This record is used when no
 * specific parameters are required for an engine operation.
 */
public record EmptyEngineParams() {
    /**
     * A default instance of EmptyEngineParams. This constant can be used when a
     * default empty parameter set is needed.
     */
    public static final EmptyEngineParams DEFAULT = new EmptyEngineParams();
}
