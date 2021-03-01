package cn.codingstyle.spider.crawl.jianshu;

import cn.codingstyle.spider.crawl.Crawler;
import cn.codingstyle.spider.crawl.PlatformPipeline;
import org.springframework.stereotype.Component;

@Component
public class JianShuCrawler extends Crawler {

    public JianShuCrawler(PlatformPipeline pipeline, JianShuProcessor processor) {
        super(pipeline, processor);
        setBaseURL("https://www.jianshu.com/");
    }

}
