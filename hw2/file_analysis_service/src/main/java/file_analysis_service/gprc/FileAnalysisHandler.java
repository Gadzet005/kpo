package file_analysis_service.gprc;

import common_lib.grpc.FileRequest;
import common_lib.grpc.FileStat;
import common_lib.grpc.WordCloud;
import common_lib.dataobjects.FileStatistics;
import common_lib.errors.NotFoundError;
import common_lib.grpc.FileAnalysisServiceGrpc;
import file_analysis_service.services.FileAnalysisService;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.protobuf.ByteString;

@GrpcService
@Slf4j
public class FileAnalysisHandler extends FileAnalysisServiceGrpc.FileAnalysisServiceImplBase {
    private final FileAnalysisService analysisService;

    @Autowired
    public FileAnalysisHandler(FileAnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @Override
    public void analyzeFile(FileRequest request, StreamObserver<FileStat> responseObserver) {
        int fileId = request.getId();

        FileStatistics result;
        try {
            result = analysisService.analyzeFile(fileId);
        } catch (NotFoundError e) {
            log.error("Файл для анализа не найден");
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
            return;
        } catch (Exception e) {
            log.error("Неизвестная ошибка", e);
            responseObserver.onError(new StatusException(Status.UNKNOWN));
            return;
        }

        var response = FileStat.newBuilder().setParagraphs(result.paragraphs()).setWords(result.words())
                .setSymbols(result.symbols()).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getFileWordCloud(FileRequest request, StreamObserver<WordCloud> responseObserver) {
        int fileId = request.getId();

        byte[] result;
        try {
            result = analysisService.getWordCloud(fileId);
        } catch (NotFoundError e) {
            log.error("Файл для анализа не найден");
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
            return;
        } catch (Exception e) {
            log.error("Неизвестная ошибка", e);
            responseObserver.onError(new StatusException(Status.UNKNOWN));
            return;
        }

        var response = WordCloud.newBuilder().setImage(ByteString.copyFrom(result)).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}