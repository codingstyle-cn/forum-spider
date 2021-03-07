package cn.codingstyle.spider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FileUtilTest {

    private FileUtil fileUtil;

    @BeforeEach
    void setUp() {

        RestTemplate restTemplate = new RestTemplate();
        fileUtil = new FileUtil(restTemplate);
    }

    @Test
    public void should_download_an_new_file() throws Exception {
        File file = fileUtil.downloadFile("https://upload-images.jianshu.io/upload_images/4790087-0a958b58ad2c6511.png");
        assertThat(file).isNotNull();
        assertThat(file.getName()).isEqualTo("4790087-0a958b58ad2c6511.png");
    }

    @Test
    public void should_not_download_file_when_the_url_is_not_correct() {
        assertThrows(HttpClientErrorException.class,
            () -> fileUtil.downloadFile("https://upload-images.jianshu.io/upload_images/404.png"));
    }
}
