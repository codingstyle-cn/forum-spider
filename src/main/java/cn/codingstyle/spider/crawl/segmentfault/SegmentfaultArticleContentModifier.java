package cn.codingstyle.spider.crawl.segmentfault;

import cn.codingstyle.spider.crawl.ArticleContentModifier;
import cn.codingstyle.spider.crawl.FileNameGenerator;
import cn.codingstyle.spider.crawl.storage.CloudStorageHelper;
import org.springframework.stereotype.Component;

@Component
public class SegmentfaultArticleContentModifier extends ArticleContentModifier {
    private final static String SOURCE = "segmentfault";

    public SegmentfaultArticleContentModifier(CloudStorageHelper cloudStorageHelper, FileNameGenerator fileNameGenerator) {
        super(cloudStorageHelper, fileNameGenerator);
    }

    @Override
    protected String modifyStyle(String content) {
        return content;
    }

    @Override
    protected String replaceImageUrl(String content, String sourceUrl, String newUrl) {
        return content.replaceAll(removeHost(sourceUrl), newUrl + "\" src=\"" + newUrl);
    }

    private String removeHost(String sourceUrl) {
        return sourceUrl.replace(SegmentfaultCrawler.BASE_URL, "");
    }

    @Override
    public String getImageType(String url) {
        return "";
    }

    @Override
    public String getSource() {
        return SOURCE;
    }
}
