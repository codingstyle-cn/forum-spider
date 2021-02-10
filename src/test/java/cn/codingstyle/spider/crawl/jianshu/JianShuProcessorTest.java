package cn.codingstyle.spider.crawl.jianshu;

import cn.codingstyle.spider.crawl.CrawlOriginalData;
import cn.codingstyle.spider.crawl.weixinmp.WeixinMpProcessor;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

public class JianShuProcessorTest {

    @Test
    void parse_content() throws IOException {
        String url = "https://www.jianshu.com/p/807997493eeb";

        JianShuProcessor processor = new JianShuProcessor();
        Page page = new Page();
        Selectable mockSelectable = Mockito.mock(Selectable.class);
        doReturn(url).when(mockSelectable).toString();
        page.setUrl(mockSelectable);

        page.setHtml(new Html(pageContent(), url));

        processor.process(page);
        CrawlOriginalData jianshuArticle = page.getResultItems().get("crawlOriginalData");
        assertThat(jianshuArticle.getAuthor()).isEqualTo("刘光聪");
        assertThat(jianshuArticle.getSubject()).isEqualTo("开发者测试：gtest与cctest");
        assertThat(trimLine(jianshuArticle.getContent())).isEqualTo(trimLine(expectedMpContent()));
    }

    private String trimLine(String content) {
        return Arrays.stream(content.split("\\n")).map(String::trim).collect(Collectors.joining());
    }

    public static String expectedMpContent() throws IOException {
        return readStringFromFile("/jianshuContent.txt");
    }


    public static String pageContent() throws IOException {
        return readStringFromFile("/jianshuPageContent.txt");
    }

    private static String readStringFromFile(String path) throws IOException {
        InputStream input = JianShuProcessorTest.class.getResourceAsStream(path);
        return IOUtils.toString(input, Charset.defaultCharset());
    }
}
