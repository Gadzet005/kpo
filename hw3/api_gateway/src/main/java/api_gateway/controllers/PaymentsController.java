package api_gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import api_gateway.clients.PaymentsServiceClient;
import common_lib.errors.ErrorCodes;
import common_lib.errors.ServiceError;
import lombok.extern.slf4j.Slf4j;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/payments")
@Slf4j
@Tag(name = "Payments API", description = "API for managing payment accounts and transactions")
public class PaymentsController {
    private final PaymentsServiceClient client;

    @Autowired
    public PaymentsController(PaymentsServiceClient client) {
        this.client = client;
    }

    @PostMapping("/create-account")
    @Operation(summary = "Create a payment account", description = "Creates a new payment account for the specified user")
    @ApiResponse(responseCode = "200", description = "Account created successfully", content = @Content(schema = @Schema(implementation = CreateAccountResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        log.debug("creating account: user_id = {}", request.userId());

        try {
            var created = client.createAccount(request.userId());
            log.debug("account successfuly created: user_id = {}", request.userId());
            return ResponseEntity.ok().body(new CreateAccountResponse(created));
        } catch (Exception e) {
            log.error("failed to create account: user_id = {}", request.userId());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/deposit")
    @Operation(summary = "Make a deposit", description = "Deposits the specified amount into the user's payment account")
    @ApiResponse(responseCode = "200", description = "Deposit successful", content = @Content(schema = @Schema(implementation = AccountBalance.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Account not found")
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<AccountBalance> deposit(@RequestBody DepositRequest request) {
        log.debug("making deposit: user_id = {}, amount = {}", request.userId(), request.amount());

        try {
            var balance = client.deposit(request.userId(), request.amount());
            log.debug("deposit successfuly done: user_id = {}, amount = {}, new_balance = {}", request.userId(),
                    request.amount(), balance);
            return ResponseEntity.ok().body(new AccountBalance(balance));
        } catch (ServiceError e) {
            if (e.getCode().equals(ErrorCodes.NOT_EXISTS)) {
                log.error("failed to make deposit: payment account not found: user_id = {}", request.userId());
                return ResponseEntity.notFound().build();
            } else if (e.getCode().equals(ErrorCodes.INVALID_ARGUMENTS)) {
                log.error("failed to make deposit: invalid arguments: user_id = {}, amount = {}", request.userId(),
                        request.amount());
                return ResponseEntity.badRequest().build();
            }

            log.error("failed to make deposit: {}", e.toString());
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            log.error("failed to make deposit", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/balance/{userId}")
    @Operation(summary = "Get account balance", description = "Retrieves the current balance of the specified user's payment account")
    @ApiResponse(responseCode = "200", description = "Balance retrieved successfully", content = @Content(schema = @Schema(implementation = AccountBalance.class)))
    @ApiResponse(responseCode = "404", description = "Account not found")
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<AccountBalance> getBalance(
            @Parameter(description = "ID of the user whose balance to retrieve", required = true) @PathVariable int userId) {
        log.debug("getting balance: user_id = {}", userId);

        try {
            var balance = client.getBalance(userId);
            return ResponseEntity.ok().body(new AccountBalance(balance));
        } catch (ServiceError e) {
            if (e.getCode().equals(ErrorCodes.NOT_EXISTS)) {
                log.error("failed to get account balance: payment account not found: user_id = {}", userId);
                return ResponseEntity.notFound().build();
            }

            log.error("failed to get account balance: {}", e.toString());
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            log.error("failed to get account balance", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}

@Schema(description = "Request to create a payment account")
record CreateAccountRequest(
        @JsonProperty("user_id") @Schema(description = "ID of the user to create an account for", example = "123") int userId) {}

@Schema(description = "Response indicating whether account was created")
record CreateAccountResponse(@Schema(description = "True if account was created, false otherwise") boolean created) {}

@Schema(description = "Request to deposit funds into an account")
record DepositRequest(
        @JsonProperty("user_id") @Schema(description = "ID of the user to deposit funds for", example = "123") int userId,
        @Schema(description = "Amount to deposit", example = "1000") int amount) {}

@Schema(description = "Account balance information")
record AccountBalance(@Schema(description = "Current account balance", example = "1500") int balance) {}