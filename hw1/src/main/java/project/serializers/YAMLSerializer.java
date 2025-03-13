package project.serializers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import project.serializers.converters.base.FieldConverter;
import project.serializers.converters.base.FieldList;

public class YAMLSerializer<T> extends BaseSerializer<T> {
    private Yaml yaml = new Yaml();

    public YAMLSerializer(FieldConverter<T> converter) {
        super(converter);
    }

    @Override
    public String serialize(T[] objects) {
        var converted = Arrays.stream(objects)
                .map(obj -> converter.convert(obj).toMap()).toArray();
        return yaml.dump(converted);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] deserialize(String yamlString) {
        var objs = (List<Map<String, Object>>) yaml.load(yamlString);
        return (T[]) objs.stream()
                .map(obj -> converter.convertBack(new FieldList(obj)))
                .toArray();
    }
}
