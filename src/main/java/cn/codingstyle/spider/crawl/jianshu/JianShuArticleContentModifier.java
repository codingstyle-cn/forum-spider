package cn.codingstyle.spider.crawl.jianshu;

import cn.codingstyle.spider.crawl.ArticleContentModifier;
import cn.codingstyle.spider.crawl.FileNameGenerator;
import cn.codingstyle.spider.crawl.storage.CloudStorageHelper;
import org.springframework.stereotype.Component;

@Component
public class JianShuArticleContentModifier extends ArticleContentModifier {
    private static String source = "jianshu";

    public JianShuArticleContentModifier(CloudStorageHelper cloudStorageHelper, FileNameGenerator fileNameGenerator) {
        super(cloudStorageHelper, fileNameGenerator);
    }

    @Override
    protected String modifyStyle(String content) {
        content = editImageHtmlLabelFormat(content);
        return removeImageCaption(content);
    }

    protected String editImageHtmlLabelFormat(String content) {
        content = content.replaceAll("data-original-src", "src")
                .replace("style=\"cursor: zoom-in;\"",
                        "style=\"padding-bottom: 25px;cursor: zoom-in;\"");
        return content;
    }

    private String removeImageCaption(String content) {
        return content.replaceAll("(<div class=\"image-caption\">)(?:(?:(?:&#160;)|(?:\\s+))*)((\\w|\\W)*?)(</div>)"
                , "");
    }

    @Override
    protected String replaceImageUrl(String content, String sourceUrl, String newUrl) {
        return content.replaceAll(sourceUrl, newUrl);
    }

    @Override
    public String getImageType(String url) {
        return url.substring(url.lastIndexOf(".") + 1);
    }

    @Override
    public String getSource() {
        return source;
    }
}
