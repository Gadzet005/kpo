package file_storing_service.grpc;

import common_lib.grpc.DownloadFileRequest;
import common_lib.grpc.FileMessage;
import common_lib.grpc.FileStoreServiceGrpc;
import common_lib.grpc.UploadFileResponse;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import file_storing_service.services.FileService;

@GrpcService
@Slf4j
public class FileStoreHandler extends FileStoreServiceGrpc.FileStoreServiceImplBase {
    private final FileService fileService;

    @Autowired
    public FileStoreHandler(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void uploadFile(FileMessage request, StreamObserver<UploadFileResponse> responseObserver) {
        var result = fileService.getOrCreateFile(request.getContent());

        UploadFileResponse response = UploadFileResponse.newBuilder().setId(result.id()).setCreated(result.created())
                .build();

        log.info("Файл успешно сохранен с ID: {}, создан: {}", response.getId(), response.getCreated());

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void downloadFile(DownloadFileRequest request, StreamObserver<FileMessage> responseObserver) {
        int fileId = request.getId();

        var content = fileService.getFileContent(fileId);
        if (!content.isPresent()) {
            log.error("Файл с ID {} не найден", fileId);
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
            return;
        }

        FileMessage response = FileMessage.newBuilder().setContent(content.get()).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}