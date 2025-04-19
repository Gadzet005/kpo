package gadzet.mini_hw_2.domain.repo;

import java.time.LocalTime;
import java.util.List;

import gadzet.mini_hw_2.domain.models.FeedingSchedule;

/**
 * Repository interface for managing feeding schedules.
 * 
 * This interface provides methods to create, save, retrieve, and remove feeding
 * schedules for animals in the system.
 */
public interface ScheduleRepo {
    record CreateScheduleData(int animalId, String foodType,
            LocalTime feedingTime) {
    }

    /**
     * Creates a new feeding schedule based on the provided schedule data.
     *
     * @param schedule The data necessary to create a new feeding schedule
     * @return The newly created FeedingSchedule object
     * @throws IllegalArgumentException if the schedule data is invalid
     */
    FeedingSchedule createSchedule(CreateScheduleData schedule)
            throws IllegalArgumentException;

    /**
     * Updates an existing feeding schedule in the repository.
     * 
     * @param schedule The feeding schedule to update
     * @return true if the schedule was successfully updated, false otherwise
     */
    boolean updateSchedule(FeedingSchedule schedule);

    /**
     * Deletes a schedule by its unique identifier.
     *
     * @param id The unique identifier of the schedule to be deleted
     * @return {@code true} if the schedule was successfully deleted,
     *         {@code false} if no schedule with the given id was found
     */
    boolean deleteScheduleById(int id);

    /**
     * Retrieves a feeding schedule by its unique identifier.
     * 
     * @param id the unique identifier of the feeding schedule to retrieve
     * @return the FeedingSchedule with the specified id, or null if no schedule
     *         is found
     */
    FeedingSchedule getScheduleById(int id);

    /**
     * Retrieves all feeding schedules for a specific animal.
     *
     * @param animalId The unique identifier of the animal
     * @return A list of feeding schedules associated with the specified animal
     */
    List<FeedingSchedule> getSchedulesForAnimal(int animalId);
}
