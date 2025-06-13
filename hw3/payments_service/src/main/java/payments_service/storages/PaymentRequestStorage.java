package payments_service.storages;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import payments_service.entities.PaymentRequest;

public interface PaymentRequestStorage extends JpaRepository<PaymentRequest, Integer> {
    public List<PaymentRequest> findAllByProcessed(boolean processed);

    @Modifying
    @Query("DELETE FROM PaymentRequest pr WHERE pr.processed = true AND pr.createdAt < :date")
    void cleanup(@Param("date") Instant date);
}