package gadzet.mini_hw_2.presentation.dto;

import java.util.List;

import gadzet.mini_hw_2.domain.models.Enclosure;
import gadzet.mini_hw_2.domain.repo.EnclosureRepo;
import gadzet.mini_hw_2.domain.valueobjects.Species;
import io.swagger.v3.oas.annotations.media.Schema;

public class EnclosureDto {
    @Schema(description = "Информация о вольере")
    public record EnclosureData(@Schema(description = "ID") int id,
            @Schema(description = "Тип") Species.Type type,
            @Schema(description = "Размер в квадратных метрах") int size,
            @Schema(description = "Максимальное количество животных") int maxAnimals,
            @Schema(description = "ID животных в вольере") List<Integer> animalIds) {

        public static EnclosureData fromEnclosure(Enclosure enclosure) {
            return new EnclosureData(enclosure.getId(), enclosure.getType(),
                    enclosure.getSize(), enclosure.getMaxAnimals(),
                    enclosure.getAnimalIds());
        }
    }

    @Schema(description = "Данные для создания вольера")
    public record CreateEnclosureData(
            @Schema(description = "Тип") Species.Type type,
            @Schema(description = "Размер в квадратных метрах") int size,
            @Schema(description = "Максимальное количество животных") int maxAnimals) {

        public EnclosureRepo.CreateEnclosureData toCreateEnclosureData() {
            return new EnclosureRepo.CreateEnclosureData(type, size,
                    maxAnimals);
        }
    }
}