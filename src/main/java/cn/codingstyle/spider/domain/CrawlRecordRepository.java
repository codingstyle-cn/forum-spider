package cn.codingstyle.spider.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlRecordRepository extends JpaRepository<CrawlRecord,Long> {
}
