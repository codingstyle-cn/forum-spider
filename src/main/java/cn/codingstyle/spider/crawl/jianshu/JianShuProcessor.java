package cn.codingstyle.spider.crawl.jianshu;

import cn.codingstyle.spider.crawl.PlatformProcessor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

@Component
public class JianShuProcessor extends PlatformProcessor {

    private final static String CRAWLING_SOURCE = "jianshu";

    @Override
    protected List<String> parseImageUrls(Html html) {
        return extractStrings(html, "//div[@class='image-view']/img/@data-original-src");
    }

    @Override
    protected String parseContent(Html html) {
        return extractString(html, "//div[@id='__next']/div[@class='_21bLU4 _3kbg6I']/div[@class='_3VRLsv']/div[@class='_gp-ck']/section[@class='ouvJEz'][1]/article[@class='_2rhmJa']");
    }

    @Override
    protected String parseSubject(Html html) {
        return extractString(html, "/html/body/div[@id='__next']/div[@class='_21bLU4 _3kbg6I']/div[@class='_3VRLsv']/div[@class='_gp-ck']/section[@class='ouvJEz'][1]/h1[@class='_1RuRku']/text()");
    }

    @Override
    protected String parseAuthor(Html html) {
        return extractString(html, "//div[@class='_21bLU4 _3kbg6I']/div[@class='_3VRLsv']/div[@class='_gp-ck']/section[@class='ouvJEz'][1]/div[@class='rEsl9f']/div[@class='_2mYfmT']/div/div[@class='_3U4Smb']/span[@class='FxYr8x']/a[@class='_1OhGeD']/text()");
    }

    @Override
    protected String getCrawlingSource() {
        return CRAWLING_SOURCE;
    }

}
