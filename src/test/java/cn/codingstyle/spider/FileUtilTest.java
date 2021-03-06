package cn.codingstyle.spider;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FileUtilTest {

    private FileUtil fileUtil;

    @BeforeEach
    void setUp() throws IOException {
        deleteImagesDir();
        RestTemplate restTemplate = new RestTemplate();
        fileUtil = new FileUtil(restTemplate);
    }

    private void deleteImagesDir() throws IOException {
        File dir = new File(FileUtil.IMAGES_DIR);
        if (dir.exists()) {
            FileUtils.forceDelete(dir);
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        deleteImagesDir();
    }

    @Test
    public void should_download_an_new_file() {
        prepareFakeFileForDeletion();

        File file = fileUtil.downloadFile("https://upload-images.jianshu.io/upload_images/4790087-0a958b58ad2c6511.png");
        assertThat(file.length()).isEqualTo(34390);
        assertThat(file.getName()).isEqualTo("4790087-0a958b58ad2c6511.png");

    }

    private void prepareFakeFileForDeletion() {
        File oldFile = new File("images/4790087-0a958b58ad2c6511.png");
        assertThat(oldFile.exists()).isFalse();
        assertThat(oldFile.mkdirs()).isTrue();
    }

    @Test
    public void should_not_download_file_when_the_url_is_not_correct() {
        assertThrows(
            HttpClientErrorException.class,
            () -> fileUtil.downloadFile("https://upload-images.jianshu.io/upload_images/404.png")
        );
    }

}
