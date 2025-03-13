package project.serializers;

public interface Serializer<T> {
    public abstract String serialize(T[] objects);

    public abstract T[] deserialize(String data);
}
