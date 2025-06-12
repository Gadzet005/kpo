package order_service.entities;

import java.time.Instant;

import common_lib.models.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "orders")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;
    private int amount;
    private String description;
    private Instant createdAt;

    @Builder.Default
    private OrderStatus status = OrderStatus.NEW;

    public common_lib.models.Order toModel() {
        return new common_lib.models.Order(id, userId, amount, description, status, createdAt);
    }
}
