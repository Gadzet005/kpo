package project.serializers.converters.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

record Field(String name, Object value) {
}

public class FieldList {
    private ArrayList<Field> fields = new ArrayList<>();

    public FieldList() {
    }

    public FieldList(Map<String, Object> m) {
        m.forEach(this::add);
    }

    public void add(String name, Object value) {
        fields.add(new Field(name, value));
    }

    public Object get(String name) {
        for (Field field : fields) {
            if (field.name().equals(name)) {
                return field.value();
            }
        }
        return null;
    }

    public String[] names() {
        return fields.stream().map(Field::name).toArray(String[]::new);
    }

    public Object[] values() {
        return fields.stream().map(Field::value).toArray(Object[]::new);
    }

    public Field[] toArray() {
        return fields.toArray(new Field[0]);
    }

    public Map<String, Object> toMap() {
        var map = new HashMap<String, Object>();
        fields.forEach(field -> map.put(field.name(), field.value()));
        return map;
    }
}
