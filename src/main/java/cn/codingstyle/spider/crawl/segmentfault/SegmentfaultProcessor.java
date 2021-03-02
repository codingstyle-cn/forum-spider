package cn.codingstyle.spider.crawl.segmentfault;

import cn.codingstyle.spider.crawl.PlatformProcessor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SegmentfaultProcessor extends PlatformProcessor {

    private final static String CRAWLING_SOURCE = "segmentfault";

    @Override
    protected List<String> parseImageUrls(Html html) {
        List<String> urls = extractStrings(html, "//div[@class='p-lg-30 position-relative card-body']/article//img/@data-src");
        return urls.stream().map(url -> SegmentfaultCrawler.BASE_URL + url).collect(Collectors.toList());
    }

    @Override
    protected String parseContent(Html html) {
        return extractString(html, "/html/body/div[@id='root']/div[@class='article-content container']/div[@class='row']/div[@class='col-12 col-xl w-0 col']/div[@class='border-0 mb-4 card']/div[@class='p-lg-30 position-relative card-body']/article");
    }

    @Override
    protected String parseSubject(Html html) {
        return extractString(html, "/html/body/div[@id='root']/div[@class='article-content container']/div[@class='row']/div[@class='col-12 col-xl w-0 col']/div[@class='border-0 mb-4 card']/div[@class='p-lg-30 position-relative card-body']/h1/a/text()");
    }

    @Override
    protected String parseAuthor(Html html) {
        return extractString(html, "/html/body/div[@id='root']/div[@class='article-content container']/div[@class='row']/div[@class='col-12 col-xl w-0 col']/div[@class='border-0 mb-4 card']/div[@class='p-lg-30 position-relative card-body']/div[@class='d-flex align-items-center mb-4']/a/strong/text()");
    }

    @Override
    protected String getCrawlingSource() {
        return CRAWLING_SOURCE;
    }

}
