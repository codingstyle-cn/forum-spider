package cn.codingstyle.spider.crawl;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlOriginalData {
    private Long recordId;
    private String author;
    private String subject;
    private String content;
    private String originalUrl;
    private List<String> imageUrls;
    private String source;
}
