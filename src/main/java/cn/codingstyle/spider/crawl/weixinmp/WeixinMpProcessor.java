package cn.codingstyle.spider.crawl.weixinmp;

import cn.codingstyle.spider.crawl.PlatformProcessor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@Component
public class WeixinMpProcessor extends PlatformProcessor {

    @Override
    protected String parseContent(Html html) {
        String content = html.xpath("//div[@id='js_content']").toString();
        return content.replaceAll(parseBr(html), "");
    }

    @Override
    protected List<String> parseImageUrls(Html html) {
        return html.xpath("//div[@class='rich_media_content']//img/@data-src").all();
    }

    private String parseBr(Html html) {
        return html.xpath("//div[@id='js_content']//p//br").toString();
    }

    @Override
    protected String parseSubject(Html html) {
        return html.xpath("//h2[@class='rich_media_title']/text()").toString().trim();
    }

    @Override
    public String parseAuthor(Html html) {
        Selectable firstAuthorHtmlFormat = html.xpath("//span[@class='rich_media_meta rich_media_meta_text']/text()");
        if (firstAuthorHtmlFormat.match()) {
            return firstAuthorHtmlFormat.toString().trim();
        }
        Selectable secondAuthorHtmlFormat = html.xpath("//span[@id='js_author_name']/text()");
        if (secondAuthorHtmlFormat.match()) {
            return secondAuthorHtmlFormat.toString().trim();
        }
        Selectable thirdAuthorHtmlFormat = html.xpath("//span[@class='rich_media_meta rich_media_meta_nickname']/a/text()");
        if (thirdAuthorHtmlFormat.match()) {
            return thirdAuthorHtmlFormat.toString().trim();
        }
        return "";
    }

}
