import { Component, Inject } from 'vue-property-decorator';
import { mixins } from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import SpiderService from '@/admin/spider/spider.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class SpiderDetailInfoComponent extends mixins(AlertMixin) {
  @Inject('spiderService') private spiderService: () => SpiderService;
  public error = '';
  public success = '';
  public crawlerDetailInfo: any = null;
  public itemsPerPage = 20;
  private detailId: number = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.detailId) {
        vm.init(to.params.detailId);
        vm.detailId = to.params.detailId;
      }
    });
  }

  public init(recordDetailId: number): void {
    this.spiderService()
      .getDetailInfo(recordDetailId)
      .then(res => {
        this.crawlerDetailInfo = res.data;
      });
  }
}
