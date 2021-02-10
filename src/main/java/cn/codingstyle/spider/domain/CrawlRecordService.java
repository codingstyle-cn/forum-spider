package cn.codingstyle.spider.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CrawlRecordService {
    private final CrawlRecordRepository crawlRecordRepository;

    public CrawlRecordService(CrawlRecordRepository crawlRecordRepository) {
        this.crawlRecordRepository = crawlRecordRepository;
    }

    public CrawlRecord saveCrawlRecord(long count, String urls) {
        CrawlRecord crawlRecord = CrawlRecord.create(count, urls);
        return crawlRecordRepository.save(crawlRecord);
    }

    public Page<CrawlRecord> getCrawlRecords(Pageable pageable) {
        return crawlRecordRepository.findAll(pageable);
    }

}
