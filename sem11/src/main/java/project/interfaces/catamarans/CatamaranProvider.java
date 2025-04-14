package project.interfaces.catamarans;

import project.domains.catamarans.Catamaran;
import project.domains.Customer;

public interface CatamaranProvider {

    /**
     * Метод покупки катамарана.
     *
     * @param customer - покупатель
     * @return - {@link Catamaran}
     */
    Catamaran takeCatamaran(Customer customer);
}
