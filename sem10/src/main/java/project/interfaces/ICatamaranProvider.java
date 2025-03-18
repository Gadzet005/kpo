package project.interfaces;

import project.domains.catamaran.Catamaran;
import project.domains.customer.Customer;

public interface ICatamaranProvider {
    Catamaran takeCatamaran(Customer customer);
}
