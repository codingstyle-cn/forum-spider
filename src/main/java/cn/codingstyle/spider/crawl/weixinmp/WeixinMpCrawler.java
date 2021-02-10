package cn.codingstyle.spider.crawl.weixinmp;

import cn.codingstyle.spider.crawl.jianshu.Crawler;
import org.springframework.stereotype.Component;

@Component
public class WeixinMpCrawler extends Crawler {

    public WeixinMpCrawler(WeixinMpPipeline weixinMpPipeline, WeixinMpProcessor processor) {
        super(weixinMpPipeline, processor);
        setBaseURL("https://mp.weixin.qq.com/");
    }

}
