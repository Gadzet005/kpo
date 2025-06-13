package payments_service.storages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import payments_service.entities.Account;

@Repository
public interface AccountStorage extends JpaRepository<Account, Integer> {
}