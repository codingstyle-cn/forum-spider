package cn.codingstyle.spider.application;

import cn.codingstyle.config.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrawlRecordDetailDto extends BaseDto {
    private Long recordId;
    private String author;
    private String subject;
    private String source;
    private String originalUrl;
    private Boolean sync;
}
