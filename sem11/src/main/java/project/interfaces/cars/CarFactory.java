package project.interfaces.cars;

import project.domains.cars.Car;

/**
 * Интерфейс для определения методов фабрик.
 *
 * @param <T> параметры для фабрик
 */
public interface CarFactory<T> {
    /**
     * Метод создания машин.
     *
     * @param carParams параметры для создания
     * @return {@link Car}
     */
    Car create(T carParams);
}
