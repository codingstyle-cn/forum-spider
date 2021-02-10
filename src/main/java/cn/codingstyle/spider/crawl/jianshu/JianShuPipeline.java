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
    protected String modifyContent(String content, List<String> imageUrls) {
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
        body = replaceImageUrl(body, currentYear, url, fileName);
        upYunHelper.uploadFile( url, path+"/"+fileName);
        return body;
    }

    protected String replaceImageUrl(String body, String currentYear, String url, String fileName) {
        body = body.replaceAll("data-original-src=\"" + url + "\""
                , "data-original-src=\"https://file.codingstyle.cn/article/photo/"
                        + currentYear + "/" + fileName + "\""
                        + " src=\"https://file.codingstyle.cn/article/photo/"
                        + currentYear + "/" + fileName + "\"");
        return body;
    }
}
