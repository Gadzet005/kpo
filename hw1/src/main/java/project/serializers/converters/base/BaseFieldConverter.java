package project.serializers.converters.base;

import java.util.ArrayList;
import java.util.List;

import project.serializers.exceptions.ConvertException;

/**
 * Convert object to list of fields and back
 */
public abstract class BaseFieldConverter<T> implements FieldConverter<T> {
    public List<FieldList> convertList(List<T> objs) throws ConvertException {
        ArrayList<FieldList> result = new ArrayList<>();
        for (T obj : objs) {
            result.add(convert(obj));
        }
        return result;
    }

    public List<T> convertBackList(List<FieldList> maps)
            throws ConvertException {
        ArrayList<T> result = new ArrayList<>();
        for (FieldList map : maps) {
            result.add(convertBack(map));
        }
        return result;
    }
}
