package cn.codingstyle.spider.application;

import cn.codingstyle.spider.domain.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CrawlRecordAppService {
    private final ModelMapper modelMapper;
    private final CrawlRecordService crawlRecordService;
    private final CrawlRecordDetailService crawlRecordDetailService;

    public CrawlRecordAppService(ModelMapper modelMapper,
                                 CrawlRecordService crawlRecordService,
                                 CrawlRecordDetailService crawlRecordDetailService) {
        this.modelMapper = modelMapper;
        this.crawlRecordService = crawlRecordService;
        this.crawlRecordDetailService = crawlRecordDetailService;
    }

    public Page<CrawlRecord> getCrawlRecords(Pageable pageable) {
        return crawlRecordService.getCrawlRecords(pageable);
    }

    public Page<CrawlRecordDetailDto> getCrawlRecordDetails(Long recordId, Pageable pageable) {
        Page<CrawlRecordDetail> page = crawlRecordDetailService.getCrawlRecordDetails(recordId, pageable);
        return page.map(this::convert);
    }

    private CrawlRecordDetailDto convert(CrawlRecordDetail detail) {
        return modelMapper.map(detail, CrawlRecordDetailDto.class);
    }

    public CrawlRecordDetail getCrawlRecordDetail(Long recordDetailId) {
        return crawlRecordDetailService.getCrawlRecordDetail(recordDetailId);
    }
}
