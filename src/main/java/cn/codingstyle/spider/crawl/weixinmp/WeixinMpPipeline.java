package cn.codingstyle.spider.crawl.weixinmp;

import cn.codingstyle.spider.crawl.storage.CloudStorageHelper;
import cn.codingstyle.spider.crawl.FileNameGenerator;
import cn.codingstyle.spider.crawl.PlatformPipeline;
import cn.codingstyle.spider.domain.CrawlRecordDetailService;
import org.springframework.stereotype.Component;

@Component
public class WeixinMpPipeline extends PlatformPipeline {

    private final static String CRAWLING_SOURCE = "weixinmp";

    public WeixinMpPipeline(CloudStorageHelper cloudStorageHelper,
                            CrawlRecordDetailService crawlRecordDetailService,
                            FileNameGenerator fileNameGenerator) {
        super(cloudStorageHelper, crawlRecordDetailService, fileNameGenerator);
    }

    @Override
    protected String modifyStyle(String content) {
        return content;
    }

    @Override
    protected String getCrawlingSource() {
        return CRAWLING_SOURCE;
    }

    @Override
    protected String replaceImageUrl(String content, String sourceUrl, String newUrl) {
        return content.replace(sourceUrl, newUrl + "\" src=\"" + newUrl);
    }

    @Override
    public String getImageType(String url) {
        return url.substring(url.lastIndexOf("=") + 1);
    }
}
