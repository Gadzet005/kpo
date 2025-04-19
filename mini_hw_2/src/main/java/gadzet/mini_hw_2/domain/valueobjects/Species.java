package gadzet.mini_hw_2.domain.valueobjects;

public record Species(Type type, String name) {
    public enum Type {
        PREDATOR, HERBO, BIRD, FISH
    }

    public boolean isCompatible(Species other) {
        return this.type == other.type;
    }

    public boolean isCompatible(Species.Type type) {
        return this.type == type;
    }
}
