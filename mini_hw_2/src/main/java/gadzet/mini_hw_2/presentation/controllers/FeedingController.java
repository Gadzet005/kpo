package gadzet.mini_hw_2.presentation.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gadzet.mini_hw_2.app.errors.ServiceError;
import gadzet.mini_hw_2.app.services.interfaces.FeedingService;
import gadzet.mini_hw_2.presentation.dto.FeedingDto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/feeding")
@Tag(name = "Feeding", description = "API для управления расписаниями кормлений")
public class FeedingController {
    private final FeedingService feedingService;

    public FeedingController(FeedingService feedingService) {
        this.feedingService = feedingService;
    }

    @Operation(summary = "Создать расписание кормлений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Расписание успешно создано"),
            @ApiResponse(responseCode = "400", description = "Ошибка при создании расписания") })
    @PostMapping()
    public void createFeedingSchedule(
            @RequestBody CreateFeedingScheduleData data) throws ServiceError {
        feedingService.createFeedingSchedule(data.toCreateScheduleData());
    }

    @Operation(summary = "Получить расписание кормлений по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Расписание успешно получено"),
            @ApiResponse(responseCode = "404", description = "Расписание не найдено") })
    @GetMapping("/{id}")
    public FeedingScheduleData getFeedingSchedule(
            @Parameter(description = "ID расписания кормлений") @PathVariable int id)
            throws ServiceError {
        var schedule = feedingService.getFeedingSchedule(id);
        return FeedingScheduleData.fromSchedule(schedule);
    }

    @Operation(summary = "Удалить расписание кормлений по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Расписание успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Расписание не найдено") })
    @DeleteMapping("/{id}")
    public void deleteFeedingSchedule(
            @Parameter(description = "ID расписания кормлений") @PathVariable int id)
            throws ServiceError {
        feedingService.deleteFeedingSchedule(id);
    }

    @Operation(summary = "Отметить кормление как выполненное")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кормление успешно отмечено как выполненное"),
            @ApiResponse(responseCode = "404", description = "Расписание не найдено") })
    @PostMapping("/{id}/mark-as-done")
    public void markFeedingAsDone(
            @Parameter(description = "ID расписания кормлений") @PathVariable int id,
            @RequestBody MarkAsDoneData data) throws ServiceError {
        feedingService.markAsDone(id, data.date());
    }
}