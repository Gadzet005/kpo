package payments_service.storages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import payments_service.entities.PaymentResult;

@Repository
public interface PaymentResultStorage extends JpaRepository<PaymentResult, Integer> {
}
