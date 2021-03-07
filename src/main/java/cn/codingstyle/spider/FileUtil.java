package cn.codingstyle.spider;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Component
@Slf4j
public class FileUtil {

    @Autowired
    private RestTemplate restTemplate;

    public FileUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public File downloadFile(String url) throws Exception {
        log.info("start download file from: {}", url);
        Instant start = Instant.now();
        String targetPath = createParentPath() + url.substring(url.lastIndexOf("/") + 1);
        RequestCallback requestCallback = request -> request.getHeaders()
            .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
        deleteFile(targetPath);
        restTemplate.execute(URI.create(url), HttpMethod.GET, requestCallback, clientHttpResponse -> {
            Files.copy(clientHttpResponse.getBody(), Paths.get(targetPath));
            return null;
        });
        log.info("file download success, cost time: {} ms", ChronoUnit.MILLIS.between(start, Instant.now()));
        return new File(targetPath);
    }

    private String createParentPath() throws IOException {
        String parentPath = "images/";
        Path path = Paths.get(parentPath);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
        return parentPath;
    }

    private void deleteFile(String targetPath) throws IOException {
        File existedFile = new File(targetPath);
        if (!existedFile.exists()) {
            return;
        }
        if (existedFile.isDirectory()) {
            FileUtils.deleteDirectory(existedFile);
        } else {
            existedFile.delete();
        }
    }
}
