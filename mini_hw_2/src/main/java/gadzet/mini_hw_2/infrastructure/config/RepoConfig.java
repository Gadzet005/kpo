package gadzet.mini_hw_2.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gadzet.mini_hw_2.domain.repo.AnimalRepo;
import gadzet.mini_hw_2.domain.repo.EnclosureRepo;
import gadzet.mini_hw_2.domain.repo.ScheduleRepo;
import gadzet.mini_hw_2.infrastructure.repo.InMemoryAnimalRepo;
import gadzet.mini_hw_2.infrastructure.repo.InMemoryEnclosureRepo;
import gadzet.mini_hw_2.infrastructure.repo.InMemoryScheduleRepo;

@Configuration
public class RepoConfig {
    @Bean
    public AnimalRepo animalRepo() {
        return new InMemoryAnimalRepo();
    }

    @Bean
    public EnclosureRepo enclosureRepo() {
        return new InMemoryEnclosureRepo();
    }

    @Bean
    public ScheduleRepo scheduleRepo() {
        return new InMemoryScheduleRepo();
    }
}
