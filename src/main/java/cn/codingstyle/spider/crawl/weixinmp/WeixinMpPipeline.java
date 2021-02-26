package cn.codingstyle.spider.crawl.weixinmp;

import cn.codingstyle.spider.application.UpYunHelper;
import cn.codingstyle.spider.crawl.PlatformPipeline;
import cn.codingstyle.spider.domain.CrawlRecordDetailService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeixinMpPipeline extends PlatformPipeline {

    private final static String CRAWLING_SOURCE = "weixinmp";
    private final FileNameGenerator fileNameGenerator;

    public WeixinMpPipeline(UpYunHelper upYunHelper,
                            CrawlRecordDetailService crawlRecordDetailService, FileNameGenerator fileNameGenerator) {
        super(upYunHelper, crawlRecordDetailService);
        this.fileNameGenerator = fileNameGenerator;
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
    protected String replaceAndUploadImage(String body, String currentYear, String url, String path) {
        String sourceUrl = url.substring(0, url.lastIndexOf("?"));
        String fileName = getFileName(url);
        String newUrl = "https://file.codingstyle.cn/article/photo/" + currentYear + "/" + fileName;
        upYunHelper.uploadFile(sourceUrl, getUploadFilePath(fileName));
        return replaceImageUrl(body, sourceUrl, newUrl);
    }

    private String getFileName(String url) {
        String imageType = url.substring(url.lastIndexOf("=") + 1);
        return fileNameGenerator.createFileName(imageType);
    }

    protected String replaceImageUrl(String body, String oldUrl, String newUrl) {
        return body.replaceAll(oldUrl, newUrl + "\" src=\"" + newUrl);
    }

    @Override
    protected String replaceImageUrl(String body, String currentYear) {
        return body;
    }
}
