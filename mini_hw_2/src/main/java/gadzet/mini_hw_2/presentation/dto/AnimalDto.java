package gadzet.mini_hw_2.presentation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import gadzet.mini_hw_2.app.events.FeedingTimeEvent;
import gadzet.mini_hw_2.app.services.interfaces.FeedingService;
import gadzet.mini_hw_2.domain.models.Animal;
import gadzet.mini_hw_2.domain.models.Animal.Gender;
import gadzet.mini_hw_2.domain.models.Animal.HealthStatus;
import gadzet.mini_hw_2.domain.repo.AnimalRepo;
import gadzet.mini_hw_2.domain.valueobjects.Species;
import io.swagger.v3.oas.annotations.media.Schema;

public class AnimalDto {
    @Schema(description = "Данные для перемещения животного", example = "{\"newEnclosureId\": 2}")
    public record MoveAnimalData(
            @Schema(description = "ID вольера, куда перемещается животное") int newEnclosureId) {
    }

    @Schema(
        description = "Данные для кормления животного", 
        example = "{\"foodType\": \"meat\", \"datetime\": \"2023-10-01T12:00:00\"}"
    )
    public record AnimalFeedData(
            @Schema(description = "Тип пищи для животного") String foodType,
            @Schema(description = "Дата и время кормления", nullable = true) LocalDateTime datetime) {
        public FeedingTimeEvent toFeedingTimeEvent(int animalId) {
            return new FeedingTimeEvent(animalId, foodType,
                    datetime == null ? LocalDateTime.now() : datetime);
        }
    }

    public record AnimalData(@Schema(description = "ID") int id,
            @Schema(description = "Кличка") String name,
            @Schema(description = "День рождения") LocalDate birthDate,
            @Schema(description = "Тип") Species.Type type,
            @Schema(description = "Порода") String species,
            @Schema(description = "Любимая еда") String favoriteFood,
            @Schema(description = "Статус здоровья") HealthStatus healthStatus,
            @Schema(description = "Пол") Gender gender,
            @Schema(description = "ID вольера") int enclosureId) {

        public static AnimalData fromAnimal(Animal animal) {
            return new AnimalData(animal.getId(), animal.getName(),
                    animal.getBirthDate(), animal.getSpecies().type(),
                    animal.getSpecies().name(), animal.getFavoriteFood(),
                    animal.getStatus(), animal.getGender(),
                    animal.getEnclosureId());
        }
    }

    public record CreateAnimalData(@Schema(description = "Кличка") String name,
            @Schema(description = "День рождения") LocalDate birthDate,
            @Schema(description = "Тип") Species.Type type,
            @Schema(description = "Порода") String species,
            @Schema(description = "Любимая еда") String favoriteFood,
            @Schema(description = "Статус здоровья") HealthStatus healthStatus,
            @Schema(description = "Пол") Gender gender,
            @Schema(description = "ID вольера") int enclosureId) {
        public AnimalRepo.CreateAnimalData toCreateAnimalData() {
            return new AnimalRepo.CreateAnimalData(name,
                    new Species(type, species), birthDate, gender, favoriteFood,
                    healthStatus, enclosureId);
        }
    }

    public record AnimalFeedingScheduleRecord(
            @Schema(description = "Время") LocalTime time,
            @Schema(description = "Тип пищи") String foodType,
            @Schema(description = "Завершено") Boolean done) {
        public static AnimalFeedingScheduleRecord fromScheduleRecord(
                FeedingService.FeedingScheduleRecord r) {
            return new AnimalFeedingScheduleRecord(r.time(), r.foodType(),
                    r.done());
        }
    }
}
