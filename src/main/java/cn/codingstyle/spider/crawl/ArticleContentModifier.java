package cn.codingstyle.spider.crawl;

import cn.codingstyle.spider.crawl.storage.CloudStorageHelper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public abstract class ArticleContentModifier {
    private final CloudStorageHelper cloudStorageHelper;
    private final FileNameGenerator fileNameGenerator;

    protected ArticleContentModifier(CloudStorageHelper cloudStorageHelper, FileNameGenerator fileNameGenerator) {
        this.cloudStorageHelper = cloudStorageHelper;
        this.fileNameGenerator = fileNameGenerator;
    }

    public abstract String getSource();

    public String modify(String content, List<String> imageUrls) {
        content = modifyImages(content, imageUrls);
        return modifyStyle(content);
    }

    protected String modifyImages(String content, List<String> imageUrls) {
        if (imageUrls.size() <= 0) {
            return content;
        }
        for (String url : imageUrls) {
            content = replaceAndUploadImage(content, url);
        }
        return content;
    }

    protected abstract String modifyStyle(String content);

    protected String replaceAndUploadImage(String content, String sourceUrl) {
        String fileName = getFileName(sourceUrl);
        cloudStorageHelper.uploadFile(sourceUrl, getUploadFilePath(fileName));
        return replaceImageUrl(content, sourceUrl, getNewUrl(fileName));
    }

    protected String getUploadFilePath(String fileName) {
        return "/article/photo/" + getCurrentYear() + "/" + fileName;
    }

    private int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    protected String getNewUrl(String fileName) {
        return "https://file.codingstyle.cn/article/photo/" + getCurrentYear() + "/" + fileName;
    }

    protected String getFileName(String url) {
        String imageType = getImageType(url);
        return fileNameGenerator.createFileName(imageType);
    }

    protected abstract String replaceImageUrl(String body, String oldUrl, String newUrl);

    public abstract String getImageType(String url);

}
