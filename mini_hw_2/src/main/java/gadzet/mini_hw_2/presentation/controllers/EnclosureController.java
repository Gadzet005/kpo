package gadzet.mini_hw_2.presentation.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gadzet.mini_hw_2.app.errors.ServiceError;
import gadzet.mini_hw_2.app.services.interfaces.EnclosureService;
import gadzet.mini_hw_2.presentation.dto.EnclosureDto.CreateEnclosureData;
import gadzet.mini_hw_2.presentation.dto.EnclosureDto.EnclosureData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/enclosures")
@Tag(name = "Enclosures", description = "API для управления вольерами")
public class EnclosureController {
    private final EnclosureService enclosureService;

    public EnclosureController(EnclosureService enclosureService) {
        this.enclosureService = enclosureService;
    }

    @Operation(summary = "Получить список всех вольеров")
    @ApiResponse(responseCode = "200", description = "Список вольеров успешно получен")
    @GetMapping
    public List<EnclosureData> getAllEnclosures() {
        var list = enclosureService.getAllEnclosures();
        return list.stream().map(EnclosureData::fromEnclosure).toList();
    }

    @Operation(summary = "Создать новый вольер")
    @ApiResponse(responseCode = "200", description = "Вольер успешно создан")
    @PostMapping
    public EnclosureData createEnclosure(@RequestBody CreateEnclosureData data)
            throws ServiceError {
        var enclosure = enclosureService
                .createEnclosure(data.toCreateEnclosureData());
        return EnclosureData.fromEnclosure(enclosure);
    }

    @Operation(summary = "Получить информацию о вольере по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вольер найден"),
            @ApiResponse(responseCode = "404", description = "Вольер не найден") })
    @GetMapping("/{id}")
    public EnclosureData getEnclosure(
            @Parameter(description = "ID вольера") @PathVariable int id)
            throws ServiceError {
        var enclosure = enclosureService.getEnclosureById(id);
        return EnclosureData.fromEnclosure(enclosure);
    }

    @Operation(summary = "Удалить вольер по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вольер успешно удален"),
            @ApiResponse(responseCode = "404", description = "Вольер не найден") })
    @DeleteMapping("/{id}")
    public void deleteEnclosure(
            @Parameter(description = "ID вольера") @PathVariable int id)
            throws ServiceError {
        enclosureService.deleteEnclosureById(id);
    }
}