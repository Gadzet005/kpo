package project.interfaces;

import project.domains.catamaran.Catamaran;

public interface ICatamaranFactory<TParams> {
    Catamaran createCar(TParams carParams, int carNumber);
}
