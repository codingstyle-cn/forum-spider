package cn.codingstyle.spider.crawl.weixinmp;

import cn.codingstyle.spider.application.UpYunHelper;
import cn.codingstyle.spider.crawl.jianshu.JianShuPipeline;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
class WeixinMpPipelineTest {

    private final String currentYear = LocalDate.now().getYear() + "";

    @Test
    void should_upload_images_to_storage_and_replace_URLs() throws IOException {
        UpYunHelper upYunHelper = mock(UpYunHelper.class);

        FileNameGenerator fileNameGenerator = mock(FileNameGenerator.class);
        String fileName = "11111.png";
        when(fileNameGenerator.createFileName("png")).thenReturn(fileName);
        WeixinMpPipeline pipeline = new WeixinMpPipeline(upYunHelper, null, fileNameGenerator);

        String content = "<img data-ratio=\"0.362962962962963\" data-src=\"https://mmbiz.qpic.cn/mmbiz_png/Oy8CSKcrQ44Mbs2MZichqVn5wbPjPAQrdPCZfusl6KKfTLJoZ6QxdXZ8bzTic6tiaZbX6TVbG1LABfYX0Btv7ial1Q/640?wx_fmt=png\" data-type=\"png\" data-w=\"1350\" style=\"display: block; margin-right: auto; margin-left: auto; zoom: 80%; width: 677px !important; height: auto !important; visibility: visible !important;\" _width=\"677px\" data-darkmode-color-16129599806201=\"rgb(163, 163, 163)\" data-darkmode-original-color-16129599806201=\"#fff|rgb(0,0,0)\" class=\"\" src=\"https://mmbiz.qpic.cn/mmbiz_png/Oy8CSKcrQ44Mbs2MZichqVn5wbPjPAQrdPCZfusl6KKfTLJoZ6QxdXZ8bzTic6tiaZbX6TVbG1LABfYX0Btv7ial1Q/640?wx_fmt=png&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1\" crossorigin=\"anonymous\" alt=\"图片\" data-fail=\"0\">";
        List<String> urls = singletonList("https://mmbiz.qpic.cn/mmbiz_png/Oy8CSKcrQ44Mbs2MZichqVn5wbPjPAQrdPCZfusl6KKfTLJoZ6QxdXZ8bzTic6tiaZbX6TVbG1LABfYX0Btv7ial1Q/640?wx_fmt=png");

        String modifiedContent = pipeline.modifyContent(content, urls);
        verify(upYunHelper).uploadFile(currentYear, "https://mmbiz.qpic.cn/mmbiz_png/Oy8CSKcrQ44Mbs2MZichqVn5wbPjPAQrdPCZfusl6KKfTLJoZ6QxdXZ8bzTic6tiaZbX6TVbG1LABfYX0Btv7ial1Q/640", fileName);
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
        return "<img data-ratio=\"0.362962962962963\" data-src=\"https://file.codingstyle.cn/article/photo/" +
            currentYear +
            "/11111.png\" src=\"https://file.codingstyle.cn/article/photo/" +
            currentYear +
            "/11111.png?wx_fmt=png\" data-type=\"png\" data-w=\"1350\" style=\"display: block; margin-right: auto; margin-left: auto; zoom: 80%; width: 677px !important; height: auto !important; visibility: visible !important;\" _width=\"677px\" data-darkmode-color-16129599806201=\"rgb(163, 163, 163)\" data-darkmode-original-color-16129599806201=\"#fff|rgb(0,0,0)\" class=\"\" src=\"https://file.codingstyle.cn/article/photo/" +
            currentYear +
            "/11111.png\" src=\"https://file.codingstyle.cn/article/photo/" +
            currentYear +
            "/11111.png?wx_fmt=png&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1\" crossorigin=\"anonymous\" alt=\"图片\" data-fail=\"0\">";
    }
}
