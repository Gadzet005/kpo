package order_service.storages;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import order_service.entities.Order;

@Repository
public interface OrderStorage extends JpaRepository<Order, Integer> {
    public List<Order> findAllByUserId(int userId);
}
