package file_storing_service.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import file_storing_service.storages.FileStorage;

@Configuration
public class AppConfig {
    @Value("${storage.location}")
    private String storageLocation;

    @Bean
    public FileStorage fileStorage() throws IOException {
        return new FileStorage(storageLocation);
    }
}
