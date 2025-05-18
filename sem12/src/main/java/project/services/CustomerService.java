package project.services;

import java.util.List;
import project.domains.Customer;
import project.dto.request.CustomerRequest;
import project.exception.KpoException;
import project.interfaces.CustomerProvider;
import project.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CustomerService implements CustomerProvider {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Transactional
    @Override
    public Customer updateCustomer(CustomerRequest request) {
        var customerOptional = customerRepository.findByName(request.getName());

        if (customerOptional.isPresent()) {
            var customer = customerOptional.get();
            customer.setIq(request.getIq());
            customer.setHandPower(request.getHandPower());
            customer.setLegPower(request.getLegPower());
            return customerRepository.save(customer);
        }
        throw new KpoException(HttpStatus.NOT_FOUND.value(),
                String.format("no customer with name: %s", request.getName()));
    }

    @Transactional
    @Override
    public boolean deleteCustomer(String name) {
        customerRepository.deleteByName(name); // Добавьте метод в
                                               // CustomerRepository
        return true;
    }
}