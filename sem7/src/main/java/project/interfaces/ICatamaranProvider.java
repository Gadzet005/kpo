package project.interfaces;

import project.domains.Catamaran;
import project.domains.Customer;

public interface ICatamaranProvider {
    Catamaran takeCatamaran(Customer customer);
}
