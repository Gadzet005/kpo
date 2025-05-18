package project.interfaces.catamarans;

import project.domains.catamarans.Catamaran;

/**
 * Интерфейс для определения методов фабрик.
 *
 * @param <T> параметры для фабрик
 */
public interface CatamaranFactory<T> {
    /**
     * Метод создания катамаранов.
     *
     * @param catamaranParams параметры для создания
     * @return {@link Catamaran}
     */
    Catamaran create(T catamaranParams);
}
