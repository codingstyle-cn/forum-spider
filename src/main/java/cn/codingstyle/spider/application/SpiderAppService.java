package cn.codingstyle.spider.application;

import cn.codingstyle.spider.crawl.jianshu.Crawler;
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
            List<String> crawlingUrls = createCrawlCommand.filterUrlsBy(crawler.getBaseURL());
            if (crawlingUrls.size() > 0) {
                crawler.crawl(crawlingUrls, savedCrawlRecord.getId());
            }
        }
    }

}
