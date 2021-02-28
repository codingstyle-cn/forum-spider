package cn.codingstyle.spider.crawl;

import cn.codingstyle.spider.domain.CrawlRecordDetail;
import cn.codingstyle.spider.domain.CrawlRecordDetailService;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * PlatformPipeline
 *
 * @author f0rb on 2021-02-06
 */
@Component
public class PlatformPipeline implements Pipeline {
    protected final CrawlRecordDetailService crawlRecordDetailService;
    private final ArticleContentModifiers articleContentModifiers;

    public PlatformPipeline(CrawlRecordDetailService crawlRecordDetailService, ArticleContentModifiers articleContentModifiers) {
        this.crawlRecordDetailService = crawlRecordDetailService;
        this.articleContentModifiers = articleContentModifiers;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        CrawlOriginalData data = resultItems.get("crawlOriginalData");
        String content = articleContentModifiers.modify(data);
        crawlRecordDetailService.save(createCrawlRecordDetail(data, content));
    }

    private CrawlRecordDetail createCrawlRecordDetail(CrawlOriginalData data, String content) {
        return CrawlRecordDetail
                .builder()
                .author(data.getAuthor())
                .subject(data.getSubject())
                .content(content)
                .sync(false)
                .originalUrl(data.getOriginalUrl())
                .recordId(data.getRecordId())
                .source(data.getSource())
                .build();
    }

}
