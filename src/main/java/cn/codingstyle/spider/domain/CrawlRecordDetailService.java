package cn.codingstyle.spider.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static cn.codingstyle.spider.domain.QCrawlRecordDetail.*;

@Service
public class CrawlRecordDetailService {
    private final CrawlRecordDetailRepository crawlRecordDetailRepository;

    public CrawlRecordDetailService(CrawlRecordDetailRepository crawlRecordDetailRepository) {
        this.crawlRecordDetailRepository = crawlRecordDetailRepository;
    }

    public Page<CrawlRecordDetail> getCrawlRecordDetails(Long recordId, Pageable pageable) {
        return crawlRecordDetailRepository.findAll(crawlRecordDetail.recordId.eq(recordId), pageable);
    }

    public CrawlRecordDetail getCrawlRecordDetail(Long recordDetailId) {
        return crawlRecordDetailRepository.findById(recordDetailId).get();
    }

    public void save(CrawlRecordDetail crawlRecordDetail) {
        crawlRecordDetailRepository.save(crawlRecordDetail);
    }

    public void sync(CrawlRecordDetail crawlRecordDetail) {
        crawlRecordDetail.sync();
        crawlRecordDetailRepository.save(crawlRecordDetail);
    }
}
