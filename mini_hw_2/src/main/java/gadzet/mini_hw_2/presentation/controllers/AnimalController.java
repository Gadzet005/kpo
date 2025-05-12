package gadzet.mini_hw_2.presentation.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import gadzet.mini_hw_2.app.errors.ServiceError;
import gadzet.mini_hw_2.app.events.AnimalMoveEvent;
import gadzet.mini_hw_2.app.services.interfaces.AnimalService;
import gadzet.mini_hw_2.app.services.interfaces.FeedingService;
import gadzet.mini_hw_2.presentation.dto.AnimalDto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/animals")
@Tag(name = "Animals", description = "API для управления животными")
public class AnimalController {
    private final AnimalService animalService;
    private final FeedingService feedingService;

    public AnimalController(AnimalService animalService,
            FeedingService feedingService) {
        this.animalService = animalService;
        this.feedingService = feedingService;
    }

    @Operation(summary = "Получить список всех животных")
    @ApiResponse(responseCode = "200", description = "Список животных успешно получен")
    @GetMapping
    public List<AnimalData> getAllAnimals() {
        var list = animalService.getAllAnimals();
        return list.stream().map(AnimalData::fromAnimal).toList();
    }

    @Operation(summary = "Создать новое животное")
    @ApiResponse(responseCode = "200", description = "Животное успешно создано")
    @PostMapping
    public AnimalData createAnimal(@RequestBody CreateAnimalData data)
            throws ServiceError {
        var animal = animalService.createAnimal(data.toCreateAnimalData());
        return AnimalData.fromAnimal(animal);
    }

    @Operation(summary = "Получить информацию о животном по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Животное найдено"),
            @ApiResponse(responseCode = "404", description = "Животное не найдено"), })
    @GetMapping("/{id}")
    public AnimalData getAnimal(
            @Parameter(description = "ID животного") @PathVariable int id)
            throws ServiceError {
        var animal = animalService.getAnimalById(id);
        return AnimalData.fromAnimal(animal);
    }

    @Operation(summary = "Удалить животное по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Животное успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Животное не найдено") })
    @DeleteMapping("/{id}")
    public void deleteAnimal(
            @Parameter(description = "ID животного") @PathVariable int id)
            throws ServiceError {
        animalService.deleteAnimalById(id);
    }

    @Operation(summary = "Переместить животное в другой вольер")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Животное успешно перемещено"),
            @ApiResponse(responseCode = "400", description = "Ошибка при перемещении") })
    @PostMapping("/{id}/move")
    public void moveAnimal(
            @Parameter(description = "ID животного") @PathVariable int id,
            @RequestBody MoveAnimalData data) throws ServiceError {
        animalService
                .moveAnimal(new AnimalMoveEvent(id, data.newEnclosureId()));
    }

    @Operation(summary = "Покормить животное")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Животное успешно покормлено"),
            @ApiResponse(responseCode = "404", description = "Животное не найдено"),
            @ApiResponse(responseCode = "400", description = "Ошибка при кормлении") })
    @PostMapping("/{id}/feed")
    public String feedAnimal(
            @Parameter(description = "ID животного") @PathVariable int id,
            @RequestBody AnimalFeedData data) throws ServiceError {
        feedingService.feedAnimal(data.toFeedingTimeEvent(id));
        return "Animal fed";
    }

    @Operation(summary = "Получить расписание кормлений животного")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Расписание успешно получено"),
            @ApiResponse(responseCode = "400", description = "Ошибка при получении расписания"),
            @ApiResponse(responseCode = "404", description = "Животное не найдено"), })
    @GetMapping("/{id}/feeding-schedule")
    public List<AnimalFeedingScheduleRecord> getFeedingSchedule(
            @Parameter(description = "ID животного") @PathVariable int id)
            throws ServiceError {
        return feedingService.getAnimalFeedingSchedule(id, LocalDate.now())
                .stream().map(AnimalFeedingScheduleRecord::fromScheduleRecord)
                .toList();
    }
}