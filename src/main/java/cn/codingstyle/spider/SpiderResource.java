package cn.codingstyle.spider;

import cn.codingstyle.spider.application.*;
import cn.codingstyle.spider.domain.CrawlRecord;
import cn.codingstyle.spider.domain.CrawlRecordDetail;
import io.github.jhipster.web.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/spider")
public class SpiderResource {

    private final SpiderAppService spiderAppService;
    private final CrawlRecordAppService crawlRecordAppService;
    private final ArticleSyncService articleSyncService;

    public SpiderResource(SpiderAppService spiderAppService,
                          CrawlRecordAppService crawlRecordAppService,
                          ArticleSyncService articleSyncService) {
        this.spiderAppService = spiderAppService;
        this.crawlRecordAppService = crawlRecordAppService;
        this.articleSyncService = articleSyncService;
    }

    @PostMapping("/crawl")
    public void crawl(@RequestBody CreateCrawlCommand createCrawlCommand) {
        spiderAppService.crawl(createCrawlCommand);
    }

    @GetMapping("/crawl-records")
    public ResponseEntity<List<CrawlRecord>> getCrawlRecordDetails(Pageable pageable) {
        final Page<CrawlRecord> page = crawlRecordAppService.getCrawlRecords(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/crawl-records/{record-id}/details")
    public ResponseEntity<List<CrawlRecordDetailDto>> getCrawlRecordDetails(@PathVariable("record-id") Long recordId, Pageable pageable) {
        final Page<CrawlRecordDetailDto> page = crawlRecordAppService.getCrawlRecordDetails(recordId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/crawl-records/details/{record-detail-id}")
    public CrawlRecordDetail getCrawlRecordDetail(@PathVariable("record-detail-id") Long recordDetailId) {
        return crawlRecordAppService.getCrawlRecordDetail(recordDetailId);
    }

    @PostMapping("/{detail-id}/sync")
    public void sync(@PathVariable("detail-id") Long detailId) {
        articleSyncService.sync(detailId);
    }

}
