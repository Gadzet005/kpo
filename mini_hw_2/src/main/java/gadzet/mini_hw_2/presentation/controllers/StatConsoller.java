package gadzet.mini_hw_2.presentation.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gadzet.mini_hw_2.app.services.interfaces.StatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

record ZooStat(int totalAnimals, int totalEnclosures, int availableEnclosures) {
}

@RestController
@RequestMapping("/api/stat")
@Tag(name = "Stat", description = "API для получения общей статистики о зоопарке")
public class StatConsoller {
    private final StatService statService;

    public StatConsoller(StatService statService) {
        this.statService = statService;
    }

    @Operation(summary = "Получить статистику зоопарка", description = "Возвращает общую информацию о количестве животных и вольеров")
    @ApiResponse(responseCode = "200", description = "Статистика успешно получена")
    @GetMapping()
    public ZooStat getZooStat() {
        int totalAnimals = statService.getTotalAnimals();
        int totalEnclosures = statService.getTotalEnclosures();
        int availableEnclosures = statService.getAvailableEnclosures();
        return new ZooStat(totalAnimals, totalEnclosures, availableEnclosures);
    }
}