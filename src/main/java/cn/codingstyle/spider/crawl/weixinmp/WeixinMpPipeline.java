package cn.codingstyle.spider.crawl.weixinmp;

import cn.codingstyle.spider.crawl.ArticleContentModifier;
import cn.codingstyle.spider.crawl.ArticleContentModifiers;
import cn.codingstyle.spider.crawl.storage.CloudStorageHelper;
import cn.codingstyle.spider.crawl.FileNameGenerator;
import cn.codingstyle.spider.crawl.PlatformPipeline;
import cn.codingstyle.spider.domain.CrawlRecordDetailService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeixinMpPipeline extends PlatformPipeline {

    public WeixinMpPipeline(CloudStorageHelper cloudStorageHelper,
                            CrawlRecordDetailService crawlRecordDetailService,
                            FileNameGenerator fileNameGenerator, List<ArticleContentModifier> articleContentModifiers) {
        super(cloudStorageHelper, crawlRecordDetailService, fileNameGenerator, new ArticleContentModifiers(articleContentModifiers));
    }

    @Override
    protected String modifyStyle(String content) {
        return content;
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
