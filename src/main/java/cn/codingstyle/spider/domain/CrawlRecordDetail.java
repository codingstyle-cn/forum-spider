package cn.codingstyle.spider.domain;

import cn.codingstyle.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "crawl_record_detail")
@Builder
@AllArgsConstructor
public class CrawlRecordDetail extends BaseEntity {
    private Long recordId;
    private String author;
    private String subject;
    @Lob
    @Column(nullable = false)
    private String content;
    private String source;
    private String originalUrl;
    private Boolean sync;

    public void sync() {
        this.sync = true;
    }

    public String getAddedReprintContent() {
        StringBuilder stringBuilder = new StringBuilder(content);
        stringBuilder.insert(0, "<p style=\"" +
            "    font-size: 1em;" +
            "    line-height: 1.8em;" +
            "    background: #f9f9f9;" +
            "    border-left: 10px solid #e87f7f;" +
            "    margin: 1.5em 10px;" +
            "    padding: 0.5em 10px;" +
            "\">\n" +
            "  作者：" + author + "<br>" +
            "  出处：<a href=\"" + originalUrl + "\" target=\"_blank\">" + originalUrl + "</a><br>" +
            "  版权：本文版权归作者所有，欢迎联系管理员认领<br></p>");

        return stringBuilder.toString();
    }
}
