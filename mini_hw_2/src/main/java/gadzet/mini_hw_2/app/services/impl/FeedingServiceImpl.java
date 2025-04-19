package gadzet.mini_hw_2.app.services.impl;

import java.time.LocalDate;
import java.util.List;

import gadzet.mini_hw_2.app.errors.ServiceError;
import gadzet.mini_hw_2.app.errors.ServiceNotFoundError;
import gadzet.mini_hw_2.app.events.FeedingTimeEvent;
import gadzet.mini_hw_2.app.services.interfaces.FeedingService;
import gadzet.mini_hw_2.domain.models.FeedingSchedule;
import gadzet.mini_hw_2.domain.repo.AnimalRepo;
import gadzet.mini_hw_2.domain.repo.ScheduleRepo;

public class FeedingServiceImpl implements FeedingService {
    private static final ServiceNotFoundError ANIMAL_NOT_FOUND = new ServiceNotFoundError(
            "Animal does not exist.");
    private static final ServiceNotFoundError SCHEDULE_NOT_FOUND = new ServiceNotFoundError(
            "Schedule does not exist.");

    private final ScheduleRepo scheduleRepo;
    private final AnimalRepo animalRepo;

    public FeedingServiceImpl(ScheduleRepo scheduleRepo,
            AnimalRepo animalRepo) {
        this.scheduleRepo = scheduleRepo;
        this.animalRepo = animalRepo;
    }

    @Override
    public void createFeedingSchedule(ScheduleRepo.CreateScheduleData data)
            throws ServiceError {
        var animal = animalRepo.getAnimalById(data.animalId());
        if (animal == null) {
            throw ANIMAL_NOT_FOUND;
        }

        try {
            scheduleRepo.createSchedule(data);
        } catch (IllegalArgumentException e) {
            throw new ServiceError("Invalid schedule data: " + e.getMessage());
        }
    }

    @Override
    public void deleteFeedingSchedule(int scheduleId) throws ServiceError {
        var result = scheduleRepo.deleteScheduleById(scheduleId);
        if (!result) {
            throw SCHEDULE_NOT_FOUND;
        }
    }

    @Override
    public FeedingSchedule getFeedingSchedule(int scheduleId)
            throws ServiceError {
        var schedule = scheduleRepo.getScheduleById(scheduleId);
        if (schedule == null) {
            throw SCHEDULE_NOT_FOUND;
        }
        return schedule;
    }

    @Override
    public void feedAnimal(FeedingTimeEvent event) throws ServiceError {
        var animal = animalRepo.getAnimalById(event.animalId());
        if (animal == null) {
            throw ANIMAL_NOT_FOUND;
        }

        animal.feed(event.foodType());
        animalRepo.updateAnimal(animal);
    }

    @Override
    public List<FeedingScheduleRecord> getAnimalFeedingSchedule(int animalId,
            LocalDate date) throws ServiceError {
        var animal = animalRepo.getAnimalById(animalId);
        if (animal == null) {
            throw ANIMAL_NOT_FOUND;
        }

        var schedules = scheduleRepo.getSchedulesForAnimal(animalId);
        return schedules.stream()
                .map(schedule -> new FeedingScheduleRecord(schedule.getTime(),
                        schedule.getFood(), schedule.isDone(date)))
                .toList();
    }

    @Override
    public void markAsDone(int scheduleId, LocalDate date) throws ServiceError {
        if (date == null) {
            date = LocalDate.now();
        }

        var schedule = scheduleRepo.getScheduleById(scheduleId);
        if (schedule == null) {
            throw SCHEDULE_NOT_FOUND;
        }

        schedule.markAsDone(date);
        scheduleRepo.updateSchedule(schedule);
    }
}
