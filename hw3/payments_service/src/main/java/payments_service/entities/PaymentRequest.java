package payments_service.entities;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "payment_requests")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    @Id
    private int orderId;

    private int userId;
    private int amount;

    @Builder.Default
    private boolean processed = false;
    private Instant createdAt;

    public common_lib.models.PaymentRequest toModel() {
        return new common_lib.models.PaymentRequest(orderId, userId, amount);
    }

    public static PaymentRequest fromModel(common_lib.models.PaymentRequest request) {
        return PaymentRequest.builder().orderId(request.orderId()).userId(request.userId()).amount(request.amount())
                .createdAt(Instant.now()).build();
    }
}