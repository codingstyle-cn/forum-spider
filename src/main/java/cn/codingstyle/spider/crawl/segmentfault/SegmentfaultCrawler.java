package cn.codingstyle.spider.crawl.segmentfault;

import cn.codingstyle.spider.crawl.Crawler;
import cn.codingstyle.spider.crawl.PlatformPipeline;
import org.springframework.stereotype.Component;

@Component
public class SegmentfaultCrawler extends Crawler {

    public static final String BASE_URL = "https://segmentfault.com/";


    public SegmentfaultCrawler(PlatformPipeline pipeline, SegmentfaultProcessor processor) {
        super(pipeline, processor);
        setBaseURL(BASE_URL);
    }

}
