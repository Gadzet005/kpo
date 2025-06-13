package payments_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "payment_results")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResult {
    @Id
    private int orderId;

    private boolean success;

    public common_lib.models.PaymentResult toModel() {
        return new common_lib.models.PaymentResult(orderId, success);
    }
}