package cn.codingstyle.spider.crawl.weixinmp;

import cn.codingstyle.spider.crawl.CrawlOriginalData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

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
    protected String parseAuthor(Html html) {
        String author = html.xpath("//span[@class='rich_media_meta rich_media_meta_text']").toString();
        String anotherAuthor = html.xpath("//span[@id='js_author_name']/text()").toString();
        if (StringUtils.isBlank(anotherAuthor)) {
            return author;
        } else {
            return anotherAuthor;
        }
    }

}
