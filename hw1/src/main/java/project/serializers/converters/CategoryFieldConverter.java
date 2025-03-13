package project.serializers.converters;

import org.springframework.stereotype.Component;

import project.consts.OperationType;
import project.domains.Category;
import project.serializers.converters.base.ConvertUtils;
import project.serializers.converters.base.FieldConverter;
import project.serializers.converters.base.FieldList;

@Component
public class CategoryFieldConverter implements FieldConverter<Category> {
    @Override
    public FieldList convert(Category obj) {
        var fields = new FieldList();
        fields.add("id", obj.getId());
        fields.add("name", obj.getName());
        fields.add("type", obj.getType().name());
        return fields;
    }

    @Override
    public Category convertBack(FieldList map) {
        var id = ConvertUtils.toInt(map.get("id"));
        var name = ConvertUtils.toString(map.get("name"));
        var type = ConvertUtils.toEnum(map.get("type"), OperationType.class);
        return new Category(id, name, type);
    }
}
