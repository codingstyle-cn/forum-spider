package cn.codingstyle.spider.crawl;

import cn.codingstyle.spider.crawl.selenium.SeleniumDownloader;
import org.springframework.beans.factory.annotation.Value;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

public class Crawler {
    protected final Pipeline pipeline;
    @Value("${selenium.host}")
    private String seleniumHost;

    private String baseURL;

    private PlatformProcessor platformProcessor;

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public Crawler(Pipeline pipeline, PlatformProcessor platformProcessor) {
        this.pipeline = pipeline;
        this.platformProcessor = platformProcessor;
    }

    public void crawl(List<String> crawlingUrls, Long crawlRecordId) {
        if (crawlingUrls.size() > 0) {
            crawl(crawlRecordId, crawlingUrls);
        }
    }

    private void crawl(Long recordId, List<String> urlList) {
        platformProcessor.setRecordId(recordId);
        Spider.create(this.platformProcessor).thread(1)
                .setDownloader(new SeleniumDownloader(seleniumHost))
                .addPipeline(pipeline)
                .addUrl(urlList.toArray(new String[urlList.size()]))
                .runAsync();
    }
}
