package gadzet.mini_hw_2.domain.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class FeedingSchedule {
    @Getter
    private final int id;
    @Getter
    private int animalId;
    @Getter
    @Setter
    private LocalTime time;
    @Getter
    private String food;

    private List<LocalDate> feedingDates = new ArrayList<>();

    public FeedingSchedule(int id, int animalId, LocalTime time, String food) {
        this.id = id;
        this.animalId = animalId;
        this.time = time;
        this.food = food;
    }

    public void markAsDone(LocalDate date) {
        if (feedingDates.contains(date)) {
            return;
        }
        feedingDates.add(date);
    }

    public boolean isDone(LocalDate date) {
        return feedingDates.contains(date);
    }
}
