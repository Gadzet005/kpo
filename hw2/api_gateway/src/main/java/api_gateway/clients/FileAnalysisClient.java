package api_gateway.clients;

import common_lib.dataobjects.FileStatistics;
import common_lib.errors.NotFoundError;
import common_lib.grpc.FileAnalysisServiceGrpc;
import common_lib.grpc.FileRequest;
import common_lib.grpc.FileStat;
import common_lib.grpc.WordCloud;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileAnalysisClient {
    private FileAnalysisServiceGrpc.FileAnalysisServiceBlockingStub analysisStub;

    public FileAnalysisClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.analysisStub = FileAnalysisServiceGrpc.newBlockingStub(channel);
    }

    public FileStatistics analyzeFile(int fileId) throws NotFoundError {
        log.info("Запрос анализа файла с ID: {}", fileId);
        FileRequest request = FileRequest.newBuilder().setId(fileId).build();

        FileStat response;
        try {
            response = analysisStub.analyzeFile(request);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new NotFoundError();
            }
            throw e;
        }

        log.info("Получена статистика для файла ID {}: абзацев {}, слов {}, символов {}", fileId,
                response.getParagraphs(), response.getWords(), response.getSymbols());

        return new FileStatistics(response.getParagraphs(), response.getWords(), response.getSymbols());
    }

    public byte[] getFileWordCloud(int fileId) throws NotFoundError {
        log.info("Запрос облака слов для файла с ID: {}", fileId);
        FileRequest request = FileRequest.newBuilder().setId(fileId).build();

        WordCloud response;
        try {
            response = analysisStub.getFileWordCloud(request);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new NotFoundError();
            }
            throw e;
        }

        byte[] imageBytes = response.getImage().toByteArray();

        log.info("Получено облако слов для файла ID {}, размер изображения: {} байт", fileId, imageBytes.length);

        return imageBytes;
    }
}
