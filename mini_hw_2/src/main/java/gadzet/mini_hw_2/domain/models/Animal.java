package gadzet.mini_hw_2.domain.models;

import java.time.LocalDate;

import gadzet.mini_hw_2.domain.valueobjects.Species;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Animal {
    public enum HealthStatus {
        HEALTHY, SICK
    }

    public enum Gender {
        MALE, FEMALE
    }

    @Getter
    private final int id;
    @Getter
    private final String name;
    @Getter
    private final Species species;
    @Getter
    private final LocalDate birthDate;
    @Getter
    private final Gender gender;
    @Getter
    private final String favoriteFood;
    @Getter
    private HealthStatus status;
    @Getter
    @Setter
    private int enclosureId;

    public void feed(String food) {
        // feed logic here...
    }

    public void heal() {
        status = HealthStatus.HEALTHY;
    }
}
