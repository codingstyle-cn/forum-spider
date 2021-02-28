package cn.codingstyle.spider.crawl.jianshu;

import cn.codingstyle.spider.crawl.storage.CloudStorageHelper;
import cn.codingstyle.spider.crawl.PlatformPipeline;
import cn.codingstyle.spider.crawl.FileNameGenerator;
import cn.codingstyle.spider.domain.CrawlRecordDetailService;
import org.springframework.stereotype.Component;

/**
 * @author Huisheng.Jin
 * @version 2018/05/07.
 */
@Component
public class JianShuPipeline extends PlatformPipeline {

    private final static String CRAWLING_SOURCE = "jianshu";

    public JianShuPipeline(CloudStorageHelper cloudStorageHelper, CrawlRecordDetailService crawlRecordDetailService,
                           FileNameGenerator fileNameGenerator) {
        super(cloudStorageHelper, crawlRecordDetailService, fileNameGenerator);
    }

    @Override
    protected String modifyStyle(String content) {
        content = editImageHtmlLabelFormat(content);
        return removeImageCaption(content);
    }

    @Override
    protected String getCrawlingSource() {
        return CRAWLING_SOURCE;
    }

    protected String editImageHtmlLabelFormat(String content) {
        content = content.replaceAll("data-original-src", "src")
                .replace("style=\"cursor: zoom-in;\"",
                        "style=\"padding-bottom: 25px;cursor: zoom-in;\"");
        return content;
    }

    private String removeImageCaption(String content) {
        return content.replaceAll("(<div class=\"image-caption\">)(?:(?:(?:&#160;)|(?:\\s+))*)((\\w|\\W)*?)(</div>)"
                , "");
    }

    @Override
    protected String replaceImageUrl(String content, String sourceUrl, String newUrl) {
        return content.replaceAll(sourceUrl, newUrl);
    }

    @Override
    public String getImageType(String url) {
        return url.substring(url.lastIndexOf(".") + 1);
    }
}
