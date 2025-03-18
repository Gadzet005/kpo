package project.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import project.domains.customer.Customer;
import project.services.CustomerStorage;
import project.swagger.CustomerRequest;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Покупатели", description = "Управление покупателями")
public class CustomerController {
    private final CustomerStorage customerService;

    @GetMapping("/{name}")
    @Operation(summary = "Получить покупателя по имени")
    public ResponseEntity<Customer> getCustomerByName(
            @PathVariable String name) {
        var customer = customerService.getCustomerByName(name);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    @Operation(summary = "Создать нового покупателя")
    public ResponseEntity<Customer> createCustomer(
            @Valid @RequestBody CustomerRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Customer customer = new Customer(request.name(), request.legPower(),
                request.handPower(), request.iq());
        customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PutMapping("/{name}")
    @Operation(summary = "Обновить данные покупателя")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String name,
            @Valid @RequestBody CustomerRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Customer customer = customerService.getCustomerByName(request.name());
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        if (request.legPower() != null) {
            customer.setLegPower(request.legPower());
        }

        if (request.handPower() != null) {
            customer.setHandPower(request.handPower());
        }

        if (request.iq() != null) {
            customer.setIq(request.iq());
        }

        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Удалить покупателя")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String name) {
        boolean deleted = customerService.deleteCustomer(name);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Получить всех покупателей")
    public List<Customer> getAllCustomers() {
        return customerService.getCustomers();
    }
}