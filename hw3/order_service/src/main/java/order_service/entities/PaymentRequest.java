package order_service.entities;

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
}
