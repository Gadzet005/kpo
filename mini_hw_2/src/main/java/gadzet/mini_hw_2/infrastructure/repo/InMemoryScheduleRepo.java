package gadzet.mini_hw_2.infrastructure.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gadzet.mini_hw_2.domain.models.FeedingSchedule;
import gadzet.mini_hw_2.domain.repo.ScheduleRepo;

public class InMemoryScheduleRepo implements ScheduleRepo {
    private final Map<Integer, FeedingSchedule> scheduleStorage = new HashMap<>();
    private int nextId = 0;

    @Override
    public FeedingSchedule createSchedule(CreateScheduleData scheduleData) {
        FeedingSchedule schedule = new FeedingSchedule(nextId++,
                scheduleData.animalId(), scheduleData.feedingTime(),
                scheduleData.foodType());
        scheduleStorage.put(schedule.getId(), schedule);
        return schedule;
    }

    @Override
    public boolean updateSchedule(FeedingSchedule schedule) {
        if (!scheduleStorage.containsKey(schedule.getId())) {
            return false;
        }
        scheduleStorage.put(schedule.getId(), schedule);
        return true;
    }

    @Override
    public boolean deleteScheduleById(int scheduleId) {
        return scheduleStorage.remove(scheduleId) != null;
    }

    @Override
    public FeedingSchedule getScheduleById(int scheduleId) {
        return scheduleStorage.get(scheduleId);
    }

    @Override
    public List<FeedingSchedule> getSchedulesForAnimal(int animalId) {
        List<FeedingSchedule> schedules = new ArrayList<>();
        for (FeedingSchedule schedule : scheduleStorage.values()) {
            if (schedule.getAnimalId() == animalId) {
                schedules.add(schedule);
            }
        }
        return schedules;
    }
}
