package project.params;

/**
 * Represents the parameters for a pedal engine. This record encapsulates the
 * configuration for a pedal-based engine system.
 */
public record PedalEngineParams(
        /**
         * The size of the pedal in the engine. This value determines the physical
         * dimensions of the pedal, typically measured in a standard unit (e.g.,
         * millimeters or inches).
         */
        int pedalSize) {
}
