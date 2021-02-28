package cn.codingstyle.spider.crawl.jianshu;

import cn.codingstyle.spider.crawl.PlatformProcessor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

@Component
public class JianShuProcessor extends PlatformProcessor {

    @Override
    protected List<String> parseImageUrls(Html html) {
        return html.xpath("//div[@class='image-view']/img/@data-original-src").all();
    }

    @Override
    protected String parseContent(Html html) {
        return html.xpath("//div[@id='__next']/div[@class='_21bLU4 _3kbg6I']/div[@class='_3VRLsv']/div[@class='_gp-ck']/section[@class='ouvJEz'][1]/article[@class='_2rhmJa']").toString();
    }

    @Override
    protected String parseSubject(Html html) {
        return html.xpath("/html/body/div[@id='__next']/div[@class='_21bLU4 _3kbg6I']/div[@class='_3VRLsv']/div[@class='_gp-ck']/section[@class='ouvJEz'][1]/h1[@class='_1RuRku']/text()").toString();
    }

    @Override
    protected String parseAuthor(Html html) {
        return html.xpath("//div[@class='_21bLU4 _3kbg6I']/div[@class='_3VRLsv']/div[@class='_gp-ck']/section[@class='ouvJEz'][1]/div[@class='rEsl9f']/div[@class='_2mYfmT']/div/div[@class='_3U4Smb']/span[@class='FxYr8x']/a[@class='_1OhGeD']/text()").toString();
    }

}
