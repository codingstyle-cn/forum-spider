package cn.codingstyle.spider.crawl;

import cn.codingstyle.spider.application.UpYunHelper;
import cn.codingstyle.spider.crawl.weixinmp.FileNameGenerator;
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
    protected final FileNameGenerator fileNameGenerator;

    public PlatformPipeline(UpYunHelper upYunHelper, CrawlRecordDetailService crawlRecordDetailService, FileNameGenerator fileNameGenerator) {
        this.upYunHelper = upYunHelper;
        this.crawlRecordDetailService = crawlRecordDetailService;
        this.fileNameGenerator = fileNameGenerator;
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
        for (String url : imageUrls) {
            body = replaceAndUploadImage(body, url);
        }
        return body;
    }

    protected String getUploadFilePath(String fileName) {
        return "/article/photo/" + getCurrentYear() + "/" + fileName;
    }

    private int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    protected abstract String replaceAndUploadImage(String body, String sourceUrl);

    protected String getNewUrl(String fileName) {
        return "https://file.codingstyle.cn/article/photo/" + getCurrentYear() + "/" + fileName;
    }

    protected String getFileName(String url) {
        String imageType = getImageType(url);
        return fileNameGenerator.createFileName(imageType);
    }

    // Weixin only
    public String getImageType(String url) {
        return url.substring(url.lastIndexOf("=") + 1);
    }
}
