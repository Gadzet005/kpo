package project.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import project.domains.car.Car;
import project.domains.engine.HandEngine;
import project.domains.engine.PedalEngine;
import project.enums.EngineTypes;
import project.interfaces.IEngine;
import project.services.CarService;
import project.services.Hse;
import project.services.HseCarService;
import project.swagger.CarRequest;
import project.domains.engine.LevitatingEngine;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@Tag(name = "Автомобили", description = "Управление транспортными средствами")
public class CarController {
    private final CarService carStorage;
    private final HseCarService carService;
    private final Hse hseFacade;

    // GET by VIN
    @GetMapping("/{vin}")
    @Operation(summary = "Получить автомобиль по VIN")
    public ResponseEntity<Car> getCarByVin(@PathVariable int vin) {
        return carStorage.getCars().stream().filter(car -> car.getVin() == vin)
                .findFirst().map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать автомобиль", description = "Для PEDAL требуется pedalSize (1-15)")
    public ResponseEntity<Car> createCar(@Valid @RequestBody CarRequest request,
            BindingResult bindingResult) {
        System.out.println(request);

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        var engineType = EngineTypes.find(request.engineType());
        if (engineType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No this type");
        }

        var car = switch (engineType.get()) {
        case EngineTypes.PEDAL -> hseFacade.addPedalCar(request.pedalSize());
        case EngineTypes.HAND -> hseFacade.addHandCar();
        case EngineTypes.LEVITATION -> hseFacade.addLevCar();
        default -> throw new RuntimeException();
        };

        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PostMapping("/sell")
    @Operation(summary = "Продать все доступные автомобили")
    public ResponseEntity<Void> sellAllCars() {
        carService.sellCars();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sell/{vin}")
    @Operation(summary = "Продать автомобиль по VIN")
    public ResponseEntity<Void> sellCar(@PathVariable int vin) {
        var carOptional = carStorage.getCars().stream()
                .filter(c -> c.getVin() == vin).findFirst();

        if (carOptional.isPresent()) {
            var car = carOptional.get();
            carStorage.getCars().remove(car);
            // Логика продажи (упрощенно)
            hseFacade.sell();
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{vin}")
    @Operation(summary = "Обновить автомобиль")
    public ResponseEntity<Car> updateCar(@PathVariable int vin,
            @Valid @RequestBody CarRequest request) {

        return carStorage.getCars().stream().filter(car -> car.getVin() == vin)
                .findFirst().map(existingCar -> {
                    var updatedCar = createCarFromRequest(request, vin);
                    carStorage.getCars().remove(existingCar);
                    carStorage.addExistingCar(updatedCar);
                    return ResponseEntity.ok(updatedCar);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{vin}")
    @Operation(summary = "Удалить автомобиль")
    public ResponseEntity<Void> deleteCar(@PathVariable int vin) {
        boolean removed = carStorage.getCars()
                .removeIf(car -> car.getVin() == vin);
        return removed ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Получить все автомобили с фильтрацией", parameters = {
            @Parameter(name = "engineType", description = "Фильтр по типу двигателя"),
            @Parameter(name = "minVin", description = "Минимальный VIN") })
    public List<Car> getAllCars(
            @RequestParam(required = false) String engineType,
            @RequestParam(required = false) Integer minVin) {

        return carStorage.getCars().stream()
                .filter(car -> engineType == null
                        || car.getEngineType().equals(engineType))
                .filter(car -> minVin == null || car.getVin() >= minVin)
                .toList();
    }

    private Car createCarFromRequest(CarRequest request, int vin) {
        IEngine engine = switch (EngineTypes.valueOf(request.engineType())) {
        case PEDAL -> new PedalEngine(request.pedalSize());
        case HAND -> new HandEngine();
        case LEVITATION -> new LevitatingEngine();
        };
        return new Car(vin, engine);
    }
}