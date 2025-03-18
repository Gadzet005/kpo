package project.serializers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import project.serializers.base.BaseSerializer;
import project.serializers.converters.base.FieldConverter;
import project.serializers.converters.base.FieldList;
import project.serializers.exceptions.ConvertException;

public class YAMLSerializer<T> extends BaseSerializer<T> {
    private Yaml yaml;

    public YAMLSerializer(FieldConverter<T> converter) {
        super(converter);

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.yaml = new Yaml(options);
    }

    @Override
    public String serialize(List<T> objects) throws ConvertException {
        var converted = converter.convertList(objects).stream()
                .map(FieldList::toMap).toList();
        return yaml.dump(converted);
    }

    @Override
    public List<T> deserialize(String yamlString) throws ConvertException {
        @SuppressWarnings("unchecked")
        var objs = (List<Map<String, Object>>) yaml.load(yamlString);

        ArrayList<T> result = new ArrayList<>();
        for (Map<String, Object> map : objs) {
            result.add(converter.convertBack(new FieldList(map)));
        }

        return result;
    }
}
