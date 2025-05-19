package common_lib.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import org.slf4j.Logger;

import common_lib.dataobjects.SaveInfo;
import common_lib.errors.NotFoundError;

public class FileStoreClient {
    private FileStoreServiceGrpc.FileStoreServiceBlockingStub fileStoreStub;
    private final org.slf4j.Logger log;

    public FileStoreClient(String host, int port, Logger log) {
        this.log = log;

        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.fileStoreStub = FileStoreServiceGrpc.newBlockingStub(channel);
    }

    public SaveInfo uploadFile(String content) {
        log.info("Uploading file: size: {}", content.length());

        FileMessage fileMessage = FileMessage.newBuilder().setContent(content).build();
        UploadFileResponse response = fileStoreStub.uploadFile(fileMessage);

        log.info("File uploaded: ID: {}, created: {}", response.getId(), response.getCreated());

        return new SaveInfo(response.getId(), response.getCreated());
    }

    public String downloadFile(int fileId) throws NotFoundError {
        log.info("Downloading file with ID: {}", fileId);

        DownloadFileRequest request = DownloadFileRequest.newBuilder().setId(fileId).build();

        try {
            FileMessage fileMessage = fileStoreStub.downloadFile(request);
            log.info("File downloaded with ID: {}, size: {}", fileId, fileMessage.getContent().length());

            return fileMessage.getContent();
        } catch (StatusRuntimeException e) {
            Status status = e.getStatus();
            if (status.getCode() == Status.Code.NOT_FOUND) {
                log.error("File with ID {} not found: {}", fileId, status.getDescription());
                throw new NotFoundError();
            }
            throw e;
        }
    }
}