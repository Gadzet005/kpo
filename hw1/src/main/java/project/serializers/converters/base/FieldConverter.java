package project.serializers.converters.base;

import java.util.List;

import project.serializers.exceptions.ConvertException;

/**
 * Convert object to list of fields and back
 */
public interface FieldConverter<T> {
    FieldList convert(T obj) throws ConvertException;

    T convertBack(FieldList map) throws ConvertException;

    List<FieldList> convertList(List<T> objs) throws ConvertException;

    List<T> convertBackList(List<FieldList> maps) throws ConvertException;
}