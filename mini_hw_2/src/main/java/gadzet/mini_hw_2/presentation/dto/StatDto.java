package gadzet.mini_hw_2.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class StatDto {

    @Schema(description = "Статистика зоопарка", example = "{\"totalAnimals\": 45, \"totalEnclosures\": 10, \"availableEnclosures\": 3}")
    public record ZooStat(
            @Schema(description = "Общее количество животных в зоопарке", example = "45") int totalAnimals,
            @Schema(description = "Общее количество вольеров в зоопарке", example = "10") int totalEnclosures,
            @Schema(description = "Количество вольеров с доступными местами", example = "3") int availableEnclosures) {
    }
}