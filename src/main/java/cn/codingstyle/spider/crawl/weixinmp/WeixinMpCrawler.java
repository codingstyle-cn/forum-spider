package cn.codingstyle.spider.crawl.weixinmp;

import cn.codingstyle.spider.crawl.Crawler;
import cn.codingstyle.spider.crawl.PlatformPipeline;
import org.springframework.stereotype.Component;

@Component
public class WeixinMpCrawler extends Crawler {

    public WeixinMpCrawler(PlatformPipeline weixinMpPipeline, WeixinMpProcessor processor) {
        super(weixinMpPipeline, processor);
        setBaseURL("https://mp.weixin.qq.com/");
    }

}
