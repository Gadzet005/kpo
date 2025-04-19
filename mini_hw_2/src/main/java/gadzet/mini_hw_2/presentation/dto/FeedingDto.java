package gadzet.mini_hw_2.presentation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import gadzet.mini_hw_2.domain.models.FeedingSchedule;
import gadzet.mini_hw_2.domain.repo.ScheduleRepo;
import io.swagger.v3.oas.annotations.media.Schema;

public class FeedingDto {

    @Schema(description = "Данные о расписании кормления")
    public record FeedingScheduleData(
            @Schema(description = "ID расписания кормления", example = "1") int scheduleId,

            @Schema(description = "ID животного", example = "42") int animalId,

            @Schema(description = "Время кормления", example = "12:30:00") LocalTime time,

            @Schema(description = "Тип корма", example = "meat") String foodType) {

        public static FeedingScheduleData fromSchedule(
                FeedingSchedule schedule) {
            return new FeedingScheduleData(schedule.getId(),
                    schedule.getAnimalId(), schedule.getTime(),
                    schedule.getFood());
        }
    }

    @Schema(description = "Данные для создания нового расписания кормления")
    public record CreateFeedingScheduleData(
            @Schema(description = "ID животного", example = "42") int animalId,

            @Schema(description = "Время кормления", example = "12:30:00") LocalTime time,

            @Schema(description = "Тип корма", example = "meat") String foodType) {

        public ScheduleRepo.CreateScheduleData toCreateScheduleData() {
            return new ScheduleRepo.CreateScheduleData(animalId(), foodType(),
                    time());
        }
    }

    public record MarkAsDoneData(
            @Schema(description = "Дата", nullable = true) LocalDate date) {
    }
}