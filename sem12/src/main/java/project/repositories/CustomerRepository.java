package project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.domains.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    /**
     * Удаляет клиента по имени
     * 
     * @param name имя клиента
     */
    void deleteByName(String name);

    /**
     * Находит клиента по имени
     * 
     * @param name имя клиента
     * @return клиент с указанным именем
     */
    Optional<Customer> findByName(String name);
}