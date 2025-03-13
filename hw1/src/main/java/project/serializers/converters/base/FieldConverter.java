package project.serializers.converters.base;

public interface FieldConverter<T> {
    FieldList convert(T obj);

    T convertBack(FieldList map);
}
