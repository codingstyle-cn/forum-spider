package cn.codingstyle.spider.crawl.jianshu;

import org.springframework.stereotype.Component;

@Component
public class JianShuCrawler extends Crawler {

    public JianShuCrawler(JianShuPipeline jianShuPipeline, JianShuProcessor processor) {
        super(jianShuPipeline, processor);
        setBaseURL("https://www.jianshu.com/");
    }

}
