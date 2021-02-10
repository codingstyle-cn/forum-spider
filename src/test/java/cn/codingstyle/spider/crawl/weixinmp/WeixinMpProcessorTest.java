package cn.codingstyle.spider.crawl.weixinmp;

import cn.codingstyle.spider.crawl.CrawlOriginalData;
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
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;

class WeixinMpProcessorTest {

    @Test
    void parse_content() throws IOException {
        String url = "https://www.jianshu.com/p/807997493eeb";

        WeixinMpProcessor processor = new WeixinMpProcessor();
        Page page = new Page();
        Selectable mockSelectable = Mockito.mock(Selectable.class);
        doReturn(url).when(mockSelectable).toString();
        page.setUrl(mockSelectable);

        page.setHtml(new Html(pageContent(), url));

        processor.process(page);
        CrawlOriginalData weixinMpArticle = page.getResultItems().get("crawlOriginalData");
        assertThat(weixinMpArticle.getAuthor()).isEqualTo("<span class=\"rich_media_meta rich_media_meta_text\"> SSgeek </span>");
        assertThat(weixinMpArticle.getSubject()).isEqualTo("Jenkins workflowLibs库的使(妙)用");
        assertThat(trimLine(weixinMpArticle.getContent())).isEqualTo(trimLine(expectedMpContent()));
    }

    private String trimLine(String content) {
        return Arrays.stream(content.split("\\n")).map(String::trim).collect(Collectors.joining());
    }

    private String expectedMpContent() throws IOException {
        return readStringFromFile("/content.txt");
    }


    private String pageContent() throws IOException {
        return readStringFromFile("/weixinMpPageContent.txt");
    }

    private String readStringFromFile(String path) throws IOException {
        InputStream input = getClass().getResourceAsStream(path);
        return IOUtils.toString(input, Charset.defaultCharset());
    }
}
