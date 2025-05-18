package api_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import api_gateway.clients.FileAnalysisClient;
import common_lib.grpc.FileStoreClient;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AppConfig {
    @Value("${grpc.file-store.host}")
    private String fileStoreHost;

    @Value("${grpc.file-store.port}")
    private int fileStorePort;

    @Value("${grpc.file-analysis.host}")
    private String fileAnalysisHost;

    @Value("${grpc.file-analysis.port}")
    private int fileAnalysisPort;

    @Bean
    public FileStoreClient fileStoreClient() {
        return new FileStoreClient(fileStoreHost, fileStorePort, log);
    }

    @Bean
    public FileAnalysisClient fileAnalysisClient() {
        return new FileAnalysisClient(fileAnalysisHost, fileAnalysisPort);
    }
}
