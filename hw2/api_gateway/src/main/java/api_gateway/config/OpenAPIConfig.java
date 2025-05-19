package api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Сервер разработки");

        Info info = new Info().title("API для работы с файлами").version("1.0")
                .description("API для загрузки, скачивания и анализа текстовых файлов");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}