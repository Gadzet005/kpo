package gadzet.mini_hw_2.app.events;

import java.time.LocalDateTime;

public record FeedingTimeEvent(int animalId, String foodType,
        LocalDateTime datetime) {
}
