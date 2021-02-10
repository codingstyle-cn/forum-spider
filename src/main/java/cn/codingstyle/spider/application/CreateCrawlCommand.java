package cn.codingstyle.spider.application;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CreateCrawlCommand {
    private String urls;

    public List<String> toUrlList() {
        return Arrays.stream(getUrls().split("\n"))
            .filter(StringUtils::isNotBlank).map(String::trim).collect(Collectors.toList());
    }

    public String getUrlsInString() {
        return urls;
    }

    public int getArticleCount() {
        return toUrlList().size();
    }

    public List<String> filterUrlsBy(String host) {
        return toUrlList().stream().filter(url -> url.startsWith(host)).collect(Collectors.toList());
    }
}
