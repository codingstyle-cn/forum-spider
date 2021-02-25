package cn.codingstyle.spider.crawl;

import cn.codingstyle.spider.application.UpYunHelper;
import cn.codingstyle.spider.domain.CrawlRecordDetail;
import cn.codingstyle.spider.domain.CrawlRecordDetailService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.time.LocalDate;
import java.util.List;

/**
 * PlatformPipeline
 *
 * @author f0rb on 2021-02-06
 */
public abstract class PlatformPipeline implements Pipeline {
    protected final UpYunHelper upYunHelper;
    protected final CrawlRecordDetailService crawlRecordDetailService;

    public PlatformPipeline(UpYunHelper upYunHelper, CrawlRecordDetailService crawlRecordDetailService) {
        this.upYunHelper = upYunHelper;
        this.crawlRecordDetailService = crawlRecordDetailService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        CrawlOriginalData data = resultItems.get("crawlOriginalData");
        String content = modifyContent(data.getContent(), data.getImageUrls());
        crawlRecordDetailService.save(createCrawlRecordDetail(data, content));
    }

    protected CrawlRecordDetail createCrawlRecordDetail(CrawlOriginalData data, String content) {
        return CrawlRecordDetail
                .builder()
                .author(data.getAuthor())
                .subject(data.getSubject())
                .content(content)
                .sync(false)
                .originalUrl(data.getOriginalUrl())
                .recordId(data.getRecordId())
                .source(getCrawlingSource())
                .build();
    }

    protected abstract String getCrawlingSource();

    protected abstract String modifyContent(String content, List<String> imageUrls);

    protected String modifyImages(String body, List<String> imageUrls) {
        if (imageUrls.size() <= 0) {
            return body;
        }
        String currentYear = String.valueOf(LocalDate.now().getYear());
        for (String url : imageUrls) {
            body = replaceAndUploadImage(body, currentYear, url, buildPath(currentYear));
        }
        body = replaceImageUrl(body, currentYear);
        return body;
    }

    protected abstract String replaceImageUrl(String body, String currentYear);

    private String buildPath(String currentYear) {
        return "/article/photo/" + currentYear;
    }

    protected abstract String replaceAndUploadImage(String body, String currentYear, String url, String path);

}
