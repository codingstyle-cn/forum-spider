package cn.codingstyle.spider.crawl;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FileNameGenerator {
    public FileNameGenerator() {
    }

    public String createFileName(String imageType) {
        return UUID.randomUUID().toString() + "." + imageType;
    }
}