package project.serializers.base;

import project.serializers.converters.base.FieldConverter;

public abstract class BaseSerializer<T> implements Serializer<T> {
    protected FieldConverter<T> converter;

    protected BaseSerializer(FieldConverter<T> converter) {
        this.converter = converter;
    }
}
