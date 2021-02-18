package cn.codingstyle.spider.crawl.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.PlainText;

import java.io.Closeable;

public class SeleniumDownloader implements Downloader, Closeable {

    private volatile WebDriverPool webDriverPool;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private int sleepTime = 200;

    private int poolSize = 1;

    private final String hostUrl;

    public SeleniumDownloader(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    /**
     * set sleep time to wait until load success
     *
     * @param sleepTime sleepTime
     * @return this
     */
    public SeleniumDownloader setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    @Override
    public Page download(Request request, Task task) {
        WebDriver webDriver = getWebDriverFromPool();
        if (webDriver == null) return null;

        loadWebPage(webDriver, request.getUrl());
        Page page = getPage(request, getOuterHTML(webDriver));
        webDriverPool.returnToPool(webDriver);
        return page;
    }

    private Page getPage(Request request, String content) {
        Page page = new Page();
        page.setRawText(content);
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        return page;
    }

    private String getOuterHTML(WebDriver webDriver) {
        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        return webElement.getAttribute("outerHTML");
    }

    private void loadWebPage(WebDriver webDriver, String requestUrl) {
        logger.info("downloading page " + requestUrl);
        webDriver.get(requestUrl);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            logger.error("Thread sleeping error", e);
        }
        logger.info("downloading page complete");

    }

    private WebDriver getWebDriverFromPool() {
        initWebDriverPool();//
        WebDriver webDriver;
        try {
            webDriver = webDriverPool.get();
        } catch (InterruptedException e) {
            logger.warn("interrupted", e);
            return null;
        }
        return webDriver;
    }

    private void initWebDriverPool() {
        if (webDriverPool == null) {
            synchronized (this) {
                webDriverPool = new WebDriverPool(hostUrl, poolSize);
            }
        }
    }

    @Override
    public void setThread(int thread) {
        this.poolSize = thread;
    }

    @Override
    public void close() {
        webDriverPool.closeAll();
    }
}
