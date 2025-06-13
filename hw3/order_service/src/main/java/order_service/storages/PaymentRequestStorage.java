package order_service.storages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import order_service.entities.PaymentRequest;

@Repository
public interface PaymentRequestStorage extends JpaRepository<PaymentRequest, Integer> {
}
