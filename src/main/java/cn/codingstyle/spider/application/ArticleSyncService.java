package cn.codingstyle.spider.application;

import cn.codingstyle.spider.client.ArticleSynchronizer;
import cn.codingstyle.spider.domain.CrawlRecordDetail;
import cn.codingstyle.spider.domain.CrawlRecordDetailService;
import org.springframework.stereotype.Service;

@Service
public class ArticleSyncService {
    private final CrawlRecordDetailService crawlRecordDetailService;
    private final ArticleSynchronizer articleSynchronizer;

    public ArticleSyncService(ArticleSynchronizer articleSynchronizer, CrawlRecordDetailService crawlRecordDetailService) {
        this.articleSynchronizer = articleSynchronizer;
        this.crawlRecordDetailService = crawlRecordDetailService;
    }

    public void sync(Long detailId) {
        CrawlRecordDetail crawlRecordDetail = crawlRecordDetailService.getCrawlRecordDetail(detailId);
        articleSynchronizer.sync(crawlRecordDetail.getSubject(), crawlRecordDetail.getAddedReprintContent());
        crawlRecordDetailService.sync(crawlRecordDetail);
    }
}
