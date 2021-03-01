package cn.codingstyle.spider.crawl.weixinmp;

import cn.codingstyle.spider.crawl.ArticleContentModifier;
import cn.codingstyle.spider.crawl.FileNameGenerator;
import cn.codingstyle.spider.crawl.storage.CloudStorageHelper;
import org.springframework.stereotype.Component;

@Component
public class WeixinMpArticleContentModifier extends ArticleContentModifier {
    private static String CRAWLING_SOURCE = "weixinmp";

    public WeixinMpArticleContentModifier(FileNameGenerator fileNameGenerator, CloudStorageHelper cloudStorageHelper) {
        super(cloudStorageHelper, fileNameGenerator);
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

    @Override
    public String getSource() {
        return CRAWLING_SOURCE;
    }
}
