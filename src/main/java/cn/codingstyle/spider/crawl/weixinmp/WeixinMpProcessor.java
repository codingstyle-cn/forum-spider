package cn.codingstyle.spider.crawl.weixinmp;

import cn.codingstyle.spider.crawl.PlatformProcessor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.selector.Html;

import java.util.Arrays;
import java.util.List;

@Component
public class WeixinMpProcessor extends PlatformProcessor {

    private final static String CRAWLING_SOURCE = "weixinmp";
    private final static List<String> AUTHOR_X_PATH_STRINGS =
            Arrays.asList(
                    "//span[@class='rich_media_meta rich_media_meta_text']/text()",
                    "//span[@id='js_author_name']/text()",
                    "//span[@class='rich_media_meta rich_media_meta_nickname']/a/text()"
            );

    @Override
    protected String parseContent(Html html) {
        String content = extractString(html, "//div[@id='js_content']");
        return content.replaceAll(parseBr(html), "");
    }

    @Override
    protected List<String> parseImageUrls(Html html) {
        return extractStrings(html, "//div[@class='rich_media_content']//img/@data-src");
    }

    private String parseBr(Html html) {
        return extractString(html, "//div[@id='js_content']//p//br");
    }

    @Override
    protected String parseSubject(Html html) {
        return extractString(html, "//h2[@class='rich_media_title']/text()");
    }

    @Override
    public String parseAuthor(Html html) {
        return AUTHOR_X_PATH_STRINGS.stream()
                .filter(xpathStr -> isMatch(html, xpathStr))
                .findAny()
                .map(xpathStr -> extractString(html, xpathStr))
                .orElse("");
    }

    private boolean isMatch(Html html, String xpath) {
        return html.xpath(xpath).match();
    }

    @Override
    protected String getCrawlingSource() {
        return CRAWLING_SOURCE;
    }

}
