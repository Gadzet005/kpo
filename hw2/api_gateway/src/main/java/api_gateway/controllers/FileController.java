package api_gateway.controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import api_gateway.clients.FileAnalysisClient;
import common_lib.dataobjects.FileStatistics;
import common_lib.dataobjects.SaveInfo;
import common_lib.errors.NotFoundError;
import common_lib.grpc.FileStoreClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Schema(description = "Результат загрузки файла")
record FileUploadResult(@Schema(description = "Идентификатор загруженного файла", example = "42") int id,
        @Schema(description = "Флаг, указывающий был ли файл создан (true) или обновлен (false)", example = "true") boolean created) {
    static FileUploadResult fromSaveInfo(SaveInfo info) {
        return new FileUploadResult(info.id(), info.created());
    }
}

@Schema(description = "Результат анализа файла")
record AnalysisResult(@Schema(description = "Количество абзацев в файле", example = "1000") int paragraphs,
        @Schema(description = "Количество слов в файле", example = "1000") int words,
        @Schema(description = "Количество символов в файле", example = "1000") int symbols) {
    static AnalysisResult fromStat(FileStatistics stat) {
        return new AnalysisResult(stat.paragraphs(), stat.words(), stat.symbols());
    }
}

@RestController
@RequestMapping("/files")
@Slf4j
@Tag(name = "Файловые операции", description = "API для работы с текстовыми файлами: загрузка, скачивание, анализ")
public class FileController {
    private final FileStoreClient fileStoreClient;
    private final FileAnalysisClient fileAnalysisClient;

    @Autowired
    public FileController(FileStoreClient fileStoreClient, FileAnalysisClient fileAnalysisClient) {
        this.fileStoreClient = fileStoreClient;
        this.fileAnalysisClient = fileAnalysisClient;
    }

    @Operation(summary = "Загрузка файла", description = "Загружает новый файл в хранилище")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл успешно загружен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileUploadResult.class))),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос или пустой файл", content = @Content(mediaType = "text/plain")) })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(
            @Parameter(description = "Файл для загрузки", required = true) @RequestParam("file") MultipartFile file) {
        log.info("Загрузка файла: {}", file.getOriginalFilename());

        if (file.getContentType() == null || !file.getContentType().startsWith("text/")) {
            log.warn("Некорректный тип файла: {}", file.getContentType());
            return ResponseEntity.badRequest().body("Некорректный тип файла. Ожидается текстовый файл");
        }

        if (file.isEmpty()) {
            log.warn("Пустой файл: {}", file.getOriginalFilename());
            return ResponseEntity.badRequest().body("Файл не должен быть пустым");
        }

        String fileContent;
        try {
            fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("invalid file");
        }

        var result = fileStoreClient.uploadFile(fileContent);

        return ResponseEntity.ok(FileUploadResult.fromSaveInfo(result));
    }

    @Operation(summary = "Скачивание файла", description = "Скачивает файл из хранилища по его идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл успешно получен", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Файл не найден", content = @Content(mediaType = "text/plain")) })
    @GetMapping("/download/{fileId}")
    public ResponseEntity<String> downloadFile(
            @Parameter(description = "Идентификатор файла", required = true, example = "42") @PathVariable int fileId) {
        log.info("Скачивание файла с ID: {}", fileId);

        String fileContent;
        try {
            fileContent = fileStoreClient.downloadFile(fileId);
        } catch (NotFoundError e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка при скачивании файла");
        }

        if (fileContent == null) {
            log.warn("Файл с ID {} не найден", fileId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Файл с ID " + fileId + " не найден");
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentLength(fileContent.length());

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    @Operation(summary = "Анализ файла", description = "Анализирует содержимое файла и возвращает статистику")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл успешно проанализирован", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnalysisResult.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка при анализе файла", content = @Content(mediaType = "text/plain")) })
    @GetMapping("/analyze/{fileId}")
    public ResponseEntity<Object> analyzeFile(
            @Parameter(description = "Идентификатор файла", required = true, example = "42") @PathVariable int fileId) {
        try {
            var stat = fileAnalysisClient.analyzeFile(fileId);
            return ResponseEntity.ok(AnalysisResult.fromStat(stat));
        } catch (NotFoundError error) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка при анализе файла");
        }
    }

    @Operation(summary = "Облако слов", description = "Генерирует облако слов на основе содержимого файла")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Облако слов успешно создано", content = @Content(mediaType = "image/png")),
            @ApiResponse(responseCode = "500", description = "Ошибка при создании облака слов", content = @Content) })
    @GetMapping("/word-cloud/{fileId}")
    public ResponseEntity<byte[]> wordCloud(
            @Parameter(description = "Идентификатор файла", required = true, example = "42") @PathVariable int fileId) {
        try {
            byte[] imageBytes = fileAnalysisClient.getFileWordCloud(fileId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(imageBytes.length);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (NotFoundError error) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Ошибка при создании облака слов для файла с ID {}: {}", fileId, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}