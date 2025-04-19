package gadzet.mini_hw_2.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gadzet.mini_hw_2.app.services.impl.AnimalServiceImpl;
import gadzet.mini_hw_2.app.services.interfaces.EnclosureService;
import gadzet.mini_hw_2.app.services.impl.EnclosureServiceImpl;
import gadzet.mini_hw_2.app.services.interfaces.FeedingService;
import gadzet.mini_hw_2.app.services.impl.FeedingServiceImpl;
import gadzet.mini_hw_2.app.services.interfaces.StatService;
import gadzet.mini_hw_2.app.services.impl.StatServiceImpl;
import gadzet.mini_hw_2.app.services.interfaces.AnimalService;
import gadzet.mini_hw_2.domain.repo.AnimalRepo;
import gadzet.mini_hw_2.domain.repo.EnclosureRepo;
import gadzet.mini_hw_2.domain.repo.ScheduleRepo;

@Configuration
public class ServiceConfig {
    @Bean
    public AnimalService animalService(EnclosureRepo enclosureRepo,
            AnimalRepo animalRepo) {
        return new AnimalServiceImpl(enclosureRepo, animalRepo);
    }

    @Bean
    public FeedingService feedingService(ScheduleRepo scheduleRepo,
            AnimalRepo animalRepo) {
        return new FeedingServiceImpl(scheduleRepo, animalRepo);
    }

    @Bean
    public StatService statService(AnimalRepo animalRepo,
            EnclosureRepo enclosureRepo) {
        return new StatServiceImpl(animalRepo, enclosureRepo);
    }

    @Bean
    public EnclosureService enclosureService(EnclosureRepo enclosureRepo) {
        return new EnclosureServiceImpl(enclosureRepo);
    }
}