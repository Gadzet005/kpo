package project.serializers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import project.serializers.base.BaseSerializer;
import project.serializers.converters.base.FieldConverter;
import project.serializers.converters.base.FieldList;
import project.serializers.exceptions.ConvertException;
import project.serializers.exceptions.SerializerException;

public class JSONSerializer<T> extends BaseSerializer<T> {
    private static TypeToken<Map<String, Object>[]> arrayType = new TypeToken<>() {
    };
    private Gson gson = new Gson();

    public JSONSerializer(FieldConverter<T> converter) {
        super(converter);
    }

    @Override
    public String serialize(List<T> objects) throws SerializerException {
        var converted = converter.convertList(objects).stream()
                .map(FieldList::toMap).toList();

        try {
            return gson.toJson(converted);
        } catch (Exception e) {
            throw new SerializerException(
                    "Error serializing to JSON: " + e.getMessage());
        }
    }

    @Override
    public List<T> deserialize(String json) throws ConvertException {
        Map<String, Object>[] array;
        try {
            array = gson.fromJson(json, arrayType);
        } catch (Exception e) {
            throw new ConvertException(
                    "Error deserializing from JSON: " + e.getMessage());
        }

        ArrayList<T> result = new ArrayList<>();
        for (Map<String, Object> map : array) {
            result.add(converter.convertBack(new FieldList(map)));
        }

        return result;
    }
}
