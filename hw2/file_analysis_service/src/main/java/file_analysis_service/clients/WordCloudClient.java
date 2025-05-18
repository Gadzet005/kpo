package file_analysis_service.clients;

import org.springframework.web.reactive.function.client.WebClient;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class WordCloudClient {
    private final WebClient webClient;

    public WordCloudClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public byte[] getWordCloudImage(String text) {
        return webClient.get().uri(uriBuilder -> uriBuilder.path("/wordcloud").queryParam("text", text)
                .queryParam("format", "png").build()).retrieve().bodyToMono(byte[].class).onErrorResume(e -> {
                    log.error("Ошибка при выполнении HTTP-запроса", e);
                    return Mono.empty();
                }).block();
    }
}