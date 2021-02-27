package cn.codingstyle.spider.crawl.jianshu;

import cn.codingstyle.spider.application.UpYunHelper;
import cn.codingstyle.spider.crawl.PlatformPipeline;
import cn.codingstyle.spider.crawl.weixinmp.FileNameGenerator;
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

    public JianShuPipeline(UpYunHelper upYunHelper, CrawlRecordDetailService crawlRecordDetailService, FileNameGenerator fileNameGenerator) {
        super(upYunHelper, crawlRecordDetailService, fileNameGenerator);
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
    protected String replaceAndUploadImage(String body, String sourceUrl) {
        /*
        String sourceUrl = url.substring(0, url.lastIndexOf("?"));
        String fileName = getFileName(url);
        String newUrl = getNewUrl(fileName);
        upYunHelper.uploadFile(sourceUrl, getUploadFilePath(fileName));
        return replaceImageUrl(body, sourceUrl, newUrl);
         */
        String fileName = sourceUrl.substring(sourceUrl.lastIndexOf("/") + 1);
        upYunHelper.uploadFile(sourceUrl, getUploadFilePath(fileName));
        body = replaceImageUrl(body, fileName, getNewUrl(fileName));
        return editImageHtmlLabelFormat(body);
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

    protected String replaceImageUrl(String body, String fileName, String newUrl) {
        return body.replaceAll("//upload-images.jianshu.io/upload_images/" + fileName,
                newUrl);
    }
}
