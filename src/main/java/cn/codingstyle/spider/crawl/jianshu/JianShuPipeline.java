package cn.codingstyle.spider.crawl.jianshu;

import cn.codingstyle.spider.application.UpYunHelper;
import cn.codingstyle.spider.crawl.PlatformPipeline;
import cn.codingstyle.spider.crawl.FileNameGenerator;
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

    public JianShuPipeline(UpYunHelper upYunHelper, CrawlRecordDetailService crawlRecordDetailService,
                           FileNameGenerator fileNameGenerator) {
        super(upYunHelper, crawlRecordDetailService, fileNameGenerator);
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

    @Override
    protected String replaceAndUploadImage(String body, String sourceUrl) {
        String fileName = getFileName(sourceUrl);
        upYunHelper.uploadFile(sourceUrl, getUploadFilePath(fileName));
        return replaceImageUrl(body, sourceUrl, getNewUrl(fileName));
    }

    protected String editImageHtmlLabelFormat(String body) {
        body = body.replaceAll("data-original-src", "src")
                .replace("style=\"cursor: zoom-in;\"",
                        "style=\"padding-bottom: 25px;cursor: zoom-in;\"");
        return body;
    }

    private String removeImageCaption(String body) {
        return body.replaceAll("(<div class=\"image-caption\">)(?:(?:(?:&#160;)|(?:\\s+))*)((\\w|\\W)*?)(</div>)"
                , "");
    }

    @Override
    protected String replaceImageUrl(String body, String oldUrl, String newUrl) {
        return body.replaceAll(oldUrl, newUrl);
    }

    @Override
    public String getImageType(String url) {
        return url.substring(url.lastIndexOf(".") + 1);
    }
}
