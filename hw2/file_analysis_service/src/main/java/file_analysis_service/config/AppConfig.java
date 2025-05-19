package file_analysis_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import common_lib.grpc.FileStoreClient;
import file_analysis_service.clients.WordCloudClient;
import file_analysis_service.storages.WordCloudCache;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AppConfig {
    @Value("${storage.location}")
    private String cacheLocation;

    @Value("${grpc.file-store.host}")
    private String fileStoreHost;

    @Value("${grpc.file-store.port}")
    private int fileStorePort;

    @Value("${external.word-cloud-url}")
    private String wordCloudUrl;

    @Bean
    public WordCloudCache wordCloudCache() {
        return new WordCloudCache(cacheLocation);
    }

    @Bean
    public FileStoreClient fileStoreClient() {
        return new FileStoreClient(fileStoreHost, fileStorePort, log);
    }

    @Bean
    public WordCloudClient wordCloudClient() {
        return new WordCloudClient(wordCloudUrl);
    }
}
