package cn.codingstyle.spider.crawl.jianshu;

import cn.codingstyle.spider.application.UpYunHelper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * JianShuPipelineTest
 *
 * @author f0rb on 2021-02-06
 */
class JianShuPipelineTest {

    private final String currentYear = LocalDate.now().getYear() + "";

    @Test
    void should_upload_images_to_storage_and_replace_URLs() {
        UpYunHelper upYunHelper = mock(UpYunHelper.class);

        JianShuPipeline jianShuPipeline = new JianShuPipeline(upYunHelper, null);

        String content = "<div class=\"image-caption\">lambda.png</div><img data-original-src=\"//upload-images.jianshu.io/upload_images/4790087-0a958b58ad2c6511.png\" data-original-width=\"384\" data-original-height=\"232\" data-original-format=\"image/png\" data-original-filesize=\"34390\" data-image-index=\"0\" style=\"cursor: zoom-in;\" class=\"\" >";
        List<String> urls = singletonList("//upload-images.jianshu.io/upload_images/4790087-0a958b58ad2c6511.png");

        String modifiedContent = jianShuPipeline.modifyContent(content, urls);
        String fileName = "/article/photo/" + currentYear + "/4790087-0a958b58ad2c6511.png";
        verify(upYunHelper).uploadFile(currentYear, "//upload-images.jianshu.io/upload_images/4790087-0a958b58ad2c6511.png", fileName);
        assertThat(modifiedContent).isEqualTo(expectedContent());
    }

    @Test
    void should_remove_image_caption() {
        UpYunHelper upYunHelper = mock(UpYunHelper.class);
        JianShuPipeline jianShuPipeline = new JianShuPipeline(upYunHelper, null);
        String content = "<div class=\"image-caption\">image.png</div>";

        String modifiedContent = jianShuPipeline.modifyContent(content, new ArrayList<>());

        assertThat(modifiedContent).isBlank();
    }

    private String expectedContent() {
        return "<img src=\"https://file.codingstyle.cn/article/photo/" +
            currentYear +
            "/4790087-0a958b58ad2c6511.png\" data-original-width=\"384\" data-original-height=\"232\" data-original-format=\"image/png\" data-original-filesize=\"34390\" data-image-index=\"0\" style=\"padding-bottom: 25px;cursor: zoom-in;\" class=\"\" >";
    }
}
