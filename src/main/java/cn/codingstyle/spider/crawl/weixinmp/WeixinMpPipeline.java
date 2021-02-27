package cn.codingstyle.spider.crawl.weixinmp;

import cn.codingstyle.spider.application.UpYunHelper;
import cn.codingstyle.spider.crawl.PlatformPipeline;
import cn.codingstyle.spider.domain.CrawlRecordDetailService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeixinMpPipeline extends PlatformPipeline {

    private final static String CRAWLING_SOURCE = "weixinmp";

    public WeixinMpPipeline(UpYunHelper upYunHelper,
                            CrawlRecordDetailService crawlRecordDetailService, FileNameGenerator fileNameGenerator) {
        super(upYunHelper, crawlRecordDetailService, fileNameGenerator);
    }

    @Override
    protected String modifyContent(String content, List<String> imageUrls) {
        return modifyImages(content, imageUrls);
    }

    @Override
    protected String getCrawlingSource() {
        return CRAWLING_SOURCE;
    }

    @Override
    protected String replaceAndUploadImage(String body, String url) {
        String sourceUrl = url.substring(0, url.lastIndexOf("?"));
        String fileName = getFileName(url);
        String newUrl = getNewUrl(fileName);
        upYunHelper.uploadFile(sourceUrl, getUploadFilePath(fileName));
        return replaceImageUrl(body, sourceUrl, newUrl);
    }

    protected String replaceImageUrl(String body, String oldUrl, String newUrl) {
        return body.replaceAll(oldUrl, newUrl + "\" src=\"" + newUrl);
    }
}
