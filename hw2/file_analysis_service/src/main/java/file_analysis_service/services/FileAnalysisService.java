package file_analysis_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import common_lib.dataobjects.FileStatistics;
import common_lib.errors.NotFoundError;
import common_lib.grpc.FileStoreClient;
import file_analysis_service.clients.WordCloudClient;
import file_analysis_service.storages.FileStatCache;
import file_analysis_service.storages.WordCloudCache;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileAnalysisService {
    private FileStatCache fileStatCache;
    private WordCloudCache wordCloudCache;
    private FileStoreClient fileStoreClient;
    private WordCloudClient wordCloudClient;

    @Autowired
    public FileAnalysisService(FileStatCache fileStatCache, WordCloudCache wordCloudCache,
            FileStoreClient fileStoreClient, WordCloudClient wordCloudClient) {
        this.fileStatCache = fileStatCache;
        this.wordCloudCache = wordCloudCache;
        this.fileStoreClient = fileStoreClient;
        this.wordCloudClient = wordCloudClient;
    }

    public FileStatistics analyzeFile(int fileId) throws NotFoundError {
        var cached = fileStatCache.findById(fileId);
        if (cached.isPresent()) {
            return new FileStatistics(cached.get().getParagraphs(), cached.get().getWords(), cached.get().getSymbols());
        }

        String file = fileStoreClient.downloadFile(fileId);

        int paragraphs = 0;
        int words = 0;
        int symbols = 0;
        boolean hasSymbol = false;

        for (int i = 0; i < file.length(); i++) {
            var cur = file.charAt(i);
            if (cur == '\n') {
                if (hasSymbol) {
                    paragraphs++;
                    words++;
                }
                hasSymbol = false;
            } else if (cur == ' ') {
                if (hasSymbol) {
                    words++;
                }
                hasSymbol = false;
            } else {
                symbols++;
                hasSymbol = true;
            }
        }

        if (hasSymbol) {
            paragraphs++;
            words++;
        }

        return new FileStatistics(paragraphs, words, symbols);
    }

    public byte[] getWordCloud(int fileId) throws NotFoundError {
        var cached = wordCloudCache.getWordCloud(fileId);
        if (cached.isPresent()) {
            log.info("Изображение word cloud найдено в кэше");
            return cached.get();
        }

        var file = fileStoreClient.downloadFile(fileId);
        var image = wordCloudClient.getWordCloudImage(file);

        wordCloudCache.saveWordCloud(fileId, image);
        return image;
    }
}
