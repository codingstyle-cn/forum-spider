package cn.codingstyle.spider.crawl.weixinmp;

import cn.codingstyle.spider.crawl.Crawler;
import cn.codingstyle.spider.crawl.PlatformPipeline;
import org.springframework.stereotype.Component;

@Component
public class WeixinMpCrawler extends Crawler {

    public WeixinMpCrawler(PlatformPipeline pipeline, WeixinMpProcessor processor) {
        super(pipeline, processor);
        setBaseURL("https://mp.weixin.qq.com/");
    }

}
