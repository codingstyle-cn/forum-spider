package cn.codingstyle.spider.crawl.weixinmp;

import cn.codingstyle.spider.application.UpYunHelper;
import cn.codingstyle.spider.crawl.PlatformPipeline;
import cn.codingstyle.spider.domain.CrawlRecordDetailService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class WeixinMpPipeline extends PlatformPipeline {

    private final static String CRAWLING_SOURCE = "weixinmp";

    public WeixinMpPipeline(UpYunHelper upYunHelper,
                            CrawlRecordDetailService crawlRecordDetailService) {
        super(upYunHelper, crawlRecordDetailService);
    }

    protected String modifyContent(String content, List<String> imageUrls) {
        return modifyImages(content, imageUrls);
    }

    @Override
    protected String getCrawlingSource() {
        return CRAWLING_SOURCE;
    }

    protected String replaceAndUploadImage(String body, String currentYear, String url, String path) {
        String imageRealUrl = url.substring(0, url.lastIndexOf("?"));
        String imageType = url.substring(url.lastIndexOf("=") + 1);
        String fileName = UUID.randomUUID().toString() + "." + imageType;
        String newUrl = "https://file.codingstyle.cn/article/photo/" + currentYear + "/" + fileName;
        body = replaceImageUrl(body, imageRealUrl, newUrl);
        upYunHelper.uploadFile2(currentYear, imageRealUrl, fileName);
        return body;
    }

    protected String replaceImageUrl(String body, String oldUrl, String newUrl) {
        body = body.replaceAll(oldUrl, newUrl + "\" src=\"" + newUrl);
        return body;
    }

}
