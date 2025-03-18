package project.swagger;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
// @formatter:off
    @Schema(description = "Имя", example = "John Doe")
    @NotNull
    String name,
    @Schema(description = "Сила ног", example = "1")
    @Nullable
    Integer legPower,
    @Schema(description = "Сила руки", example = "2")
    @Nullable
    Integer handPower,
    @Schema(description = "Интеллект", example = "100")
    @Nullable
    Integer iq
// @formatter:on
) {
}