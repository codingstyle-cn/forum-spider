package cn.codingstyle.spider.crawl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

@Slf4j
public abstract class PlatformProcessor implements PageProcessor {
    private Long recordId;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    @Override
    public Site getSite() {
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36";
        return Site.me()
                .setSleepTime(100)
                .setUserAgent(userAgent);
    }

    protected CrawlOriginalData parseOriginalData(Page page) {
        String originalUrl = page.getUrl().toString();
        Html html = page.getHtml();
        String author = parseAuthor(html);
        String subject = parseSubject(html);
        String content = parseContent(html);
        String source = getCrawlingSource();
        List<String> imageUrls = parseImageUrls(html);
        log.info("author: {}, article content is not blank? : {}", author, StringUtils.isNotBlank(content));
        return createCrawlOriginalData(originalUrl, author, subject, content, imageUrls, source);
    }

    protected CrawlOriginalData createCrawlOriginalData(String originalUrl, String author,
                                                        String subject, String content,
                                                        List<String> imageUrls, String source) {
        return CrawlOriginalData.builder()
                .author(author)
                .subject(subject)
                .content(content)
                .imageUrls(imageUrls)
                .originalUrl(originalUrl)
                .recordId(getRecordId())
                .source(source)
                .build();
    }

    @Override
    public void process(Page page) {
        CrawlOriginalData crawlOriginalData = parseOriginalData(page);
        page.putField("crawlOriginalData", crawlOriginalData);
    }

    protected abstract List<String> parseImageUrls(Html html);

    protected abstract String parseContent(Html html);

    protected abstract String parseSubject(Html html);

    protected abstract String parseAuthor(Html html);

    protected abstract String getCrawlingSource();

}
