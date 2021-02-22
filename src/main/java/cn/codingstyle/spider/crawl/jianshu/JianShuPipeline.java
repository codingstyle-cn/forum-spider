package cn.codingstyle.spider.crawl.jianshu;

import cn.codingstyle.spider.application.UpYunHelper;
import cn.codingstyle.spider.crawl.PlatformPipeline;
import cn.codingstyle.spider.domain.CrawlRecordDetailService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Huisheng.Jin
 * @version 2018/05/07.
 */
@Component
public class JianShuPipeline extends PlatformPipeline {

    private final static String CRAWLING_SOURCE = "jianshu";

    public JianShuPipeline(UpYunHelper upYunHelper, CrawlRecordDetailService crawlRecordDetailService) {
        super(upYunHelper, crawlRecordDetailService);
    }

    @Override
    public String modifyContent(String content, List<String> imageUrls) {
        content = modifyImages(content, imageUrls);
        return removeImageCaption(content);
    }

    @Override
    protected String getCrawlingSource() {
        return CRAWLING_SOURCE;
    }

    @Override
    protected String replaceAndUploadImage(String body, String currentYear, String url, String path) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        upYunHelper.uploadFile(currentYear, url, path + "/" + fileName);
        return replaceImageUrl(body, currentYear, url, fileName);
    }

    protected String replaceImageUrl(String body, String currentYear, String url, String fileName) {
        body = body.replaceAll("data-original-src", "src")
                .replace("style=\"cursor: zoom-in;\"", "style=\"padding-bottom: 25px;cursor: zoom-in;\"");
        return body;
    }
}
