package cn.codingstyle.spider.crawl;

import cn.codingstyle.spider.crawl.storage.CloudStorageHelper;
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
    protected final CloudStorageHelper cloudStorageHelper;
    protected final CrawlRecordDetailService crawlRecordDetailService;
    protected final FileNameGenerator fileNameGenerator;

    public PlatformPipeline(CloudStorageHelper cloudStorageHelper, CrawlRecordDetailService crawlRecordDetailService, FileNameGenerator fileNameGenerator) {
        this.cloudStorageHelper = cloudStorageHelper;
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

    public String modifyContent(String content, List<String> imageUrls) {
        content = modifyImages(content, imageUrls);
        content = modifyStyle(content);
        return content;
    }

    protected String modifyImages(String content, List<String> imageUrls) {
        if (imageUrls.size() <= 0) {
            return content;
        }
        for (String url : imageUrls) {
            content = replaceAndUploadImage(content, url);
        }
        return content;
    }

    protected abstract String modifyStyle(String content);

    protected String replaceAndUploadImage(String content, String sourceUrl) {
        String fileName = getFileName(sourceUrl);
        cloudStorageHelper.uploadFile(sourceUrl, getUploadFilePath(fileName));
        return replaceImageUrl(content, sourceUrl, getNewUrl(fileName));
    }

    protected String getUploadFilePath(String fileName) {
        return "/article/photo/" + getCurrentYear() + "/" + fileName;
    }

    private int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    protected String getNewUrl(String fileName) {
        return "https://file.codingstyle.cn/article/photo/" + getCurrentYear() + "/" + fileName;
    }

    protected String getFileName(String url) {
        String imageType = getImageType(url);
        return fileNameGenerator.createFileName(imageType);
    }

    protected abstract String replaceImageUrl(String body, String oldUrl, String newUrl);

    public abstract String getImageType(String url);
}
