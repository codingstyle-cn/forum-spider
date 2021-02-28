package cn.codingstyle.spider.crawl.jianshu;

import cn.codingstyle.spider.application.UpYunHelper;
import cn.codingstyle.spider.crawl.FileNameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * JianShuPipelineTest
 *
 * @author f0rb on 2021-02-06
 */
class JianShuPipelineTest {

    private final String currentYear = LocalDate.now().getYear() + "";
    private JianShuPipeline pipeline;
    private UpYunHelper upYunHelper;
    private FileNameGenerator fileNameGenerator;


    @BeforeEach
    void setUp() {
        upYunHelper = mock(UpYunHelper.class);
        fileNameGenerator = mock(FileNameGenerator.class);
        pipeline = new JianShuPipeline(upYunHelper, null, fileNameGenerator);
    }

    @Test
    void should_upload_images_to_storage_and_replace_URLs() {
        String content = "<div class=\"image-caption\">lambda.png</div><img data-original-src=\"//upload-images.jianshu.io/upload_images/4790087-0a958b58ad2c6511.png\" data-original-width=\"384\" data-original-height=\"232\" data-original-format=\"image/png\" data-original-filesize=\"34390\" data-image-index=\"0\" style=\"cursor: zoom-in;\" class=\"\" >";
        List<String> urls = singletonList("//upload-images.jianshu.io/upload_images/4790087-0a958b58ad2c6511.png");
        String fileName = "4790087-0a958b58ad2c6511.png";
        when(fileNameGenerator.createFileName("png")).thenReturn(fileName);
        String modifiedContent = pipeline.modifyContent(content, urls);
        verify(upYunHelper).uploadFile("//upload-images.jianshu.io/upload_images/4790087-0a958b58ad2c6511.png",
            "/article/photo/" + LocalDate.now().getYear() + "/" + fileName);

        assertThat(modifiedContent).isEqualTo(expectedContent());
    }

    @Test
    void should_remove_image_caption() {
        String content = "<div class=\"image-caption\">image.png</div>";
        String modifiedContent = pipeline.modifyContent(content, new ArrayList<>());
        assertThat(modifiedContent).isBlank();
    }

    private String expectedContent() {
        return "<img src=\"https://file.codingstyle.cn/article/photo/" +
            currentYear +
            "/4790087-0a958b58ad2c6511.png\" data-original-width=\"384\" data-original-height=\"232\" data-original-format=\"image/png\" data-original-filesize=\"34390\" data-image-index=\"0\" style=\"padding-bottom: 25px;cursor: zoom-in;\" class=\"\" >";
    }

    @Test
    void should_get_image_type() {
        String imageType = pipeline.getImageType("//upload-images.jianshu.io/upload_images/4790087-0a958b58ad2c6511.png");
        assertThat(imageType).isEqualTo("png");
    }


}
