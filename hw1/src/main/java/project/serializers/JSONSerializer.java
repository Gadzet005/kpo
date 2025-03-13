package project.serializers;

import java.util.Arrays;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import project.serializers.converters.base.FieldConverter;
import project.serializers.converters.base.FieldList;

public class JSONSerializer<T> extends BaseSerializer<T> {
    private static TypeToken<Map<String, Object>[]> arrayType = new TypeToken<>() {
    };
    private Gson gson = new Gson();

    public JSONSerializer(FieldConverter<T> converter) {
        super(converter);
    }

    @Override
    public String serialize(T[] objects) {
        var converted = Arrays.stream(objects)
                .map(obj -> converter.convert(obj).toMap()).toArray();
        return gson.toJson(converted);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] deserialize(String json) {
        var array = gson.fromJson(json, arrayType);
        return (T[]) Arrays.stream(array)
                .map(map -> converter.convertBack(new FieldList(map)))
                .toArray();
    }
}
