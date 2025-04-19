package gadzet.mini_hw_2.app.services.interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import gadzet.mini_hw_2.app.errors.ServiceError;
import gadzet.mini_hw_2.app.events.FeedingTimeEvent;
import gadzet.mini_hw_2.domain.models.FeedingSchedule;
import gadzet.mini_hw_2.domain.repo.ScheduleRepo;

public interface FeedingService {
    public record FeedingScheduleRecord(LocalTime time, String foodType,
            boolean done) {
    }

    void createFeedingSchedule(ScheduleRepo.CreateScheduleData data)
            throws ServiceError;

    FeedingSchedule getFeedingSchedule(int id) throws ServiceError;

    void deleteFeedingSchedule(int id) throws ServiceError;

    void feedAnimal(FeedingTimeEvent event) throws ServiceError;

    List<FeedingScheduleRecord> getAnimalFeedingSchedule(int animalId,
            LocalDate date) throws ServiceError;

    void markAsDone(int scheduleId, LocalDate date) throws ServiceError;
}