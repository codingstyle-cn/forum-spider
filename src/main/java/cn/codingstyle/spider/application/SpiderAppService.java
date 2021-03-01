package cn.codingstyle.spider.application;

import cn.codingstyle.spider.crawl.Crawler;
import cn.codingstyle.spider.domain.CrawlRecord;
import cn.codingstyle.spider.domain.CrawlRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpiderAppService {
    private final List<Crawler> crawlers;
    private final CrawlRecordService crawlRecordService;

    public SpiderAppService(List<Crawler> crawlers,
                            CrawlRecordService crawlRecordService) {
        this.crawlers = crawlers;
        this.crawlRecordService = crawlRecordService;
    }

    public void crawl(CreateCrawlCommand createCrawlCommand) {
        CrawlRecord savedCrawlRecord = crawlRecordService.saveCrawlRecord(
                createCrawlCommand.getArticleCount(),
                createCrawlCommand.getUrlsInString());
        for (Crawler crawler : crawlers) {
            crawl(savedCrawlRecord.getId(), createCrawlCommand, crawler);
        }
    }

    private void crawl(Long crawlRecordId, CreateCrawlCommand createCrawlCommand, Crawler crawler) {
        List<String> crawlingUrls = createCrawlCommand.filterUrlsBy(crawler.getBaseURL());
        crawler.crawl(crawlingUrls, crawlRecordId);
    }

}
