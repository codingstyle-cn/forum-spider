package cn.codingstyle.spider.domain;

import cn.codingstyle.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "crawl_record")
@Builder
@AllArgsConstructor
public class CrawlRecord extends BaseEntity {
    private Long count;
    @Lob
    private String crawlUrls;

    public static CrawlRecord create(long count, String urls) {
        return builder()
            .count(count)
            .crawlUrls(urls)
            .build();
    }
}
