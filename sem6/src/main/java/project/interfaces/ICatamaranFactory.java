package project.interfaces;

import project.domains.Catamaran;

public interface ICatamaranFactory<TParams> {
    Catamaran createCar(TParams carParams, int carNumber);
}
