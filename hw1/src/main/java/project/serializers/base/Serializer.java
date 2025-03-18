package project.serializers.base;

import java.util.List;

import project.serializers.exceptions.SerializerException;

/**
 * This interface defines the contract for a serializer that can serialize and
 * deserialize a list of objects of type T.
 *
 * @param <T> The type of objects to be serialized and deserialized.
 */
public interface Serializer<T> {

    /**
     * Serializes a list of objects into a string representation.
     *
     * @param objects The list of objects to be serialized.
     * @return A string representation of the list of objects.
     */
    public abstract String serialize(List<T> objects)
            throws SerializerException;

    /**
     * Deserializes a string representation into a list of objects.
     *
     * @param data The string representation of the list of objects.
     * @return A list of objects deserialized from the string representation.
     */
    public abstract List<T> deserialize(String data) throws SerializerException;
}
