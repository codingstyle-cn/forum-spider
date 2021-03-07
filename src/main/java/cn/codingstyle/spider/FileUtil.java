package cn.codingstyle.spider;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Component
@Slf4j
public class FileUtil {

    private static final String IMAGES_DIR = "images/";

    private final RestTemplate restTemplate;

    public FileUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        createParentPath();
    }

    private void createParentPath() {
        new File(IMAGES_DIR).mkdirs();
    }

    public File downloadFile(String url) throws Exception {
        log.info("start download file from: {}", url);
        Instant start = Instant.now();
        String targetPath = IMAGES_DIR + url.substring(url.lastIndexOf("/") + 1);
        deleteFile(targetPath);
        restTemplate.execute(
            URI.create(url),
            HttpMethod.GET,
            request -> request.getHeaders()
                .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL)),
            clientHttpResponse -> {
                Files.copy(clientHttpResponse.getBody(), Paths.get(targetPath));
                return null;
            }
        );
        log.info("file download success, cost time: {} ms", ChronoUnit.MILLIS.between(start, Instant.now()));
        return new File(targetPath);
    }

    private void deleteFile(String targetPath) {
        log.debug("targetPath = {}", targetPath);
        File existedFile = new File(targetPath);
        FileUtils.deleteQuietly(existedFile);
    }
}
