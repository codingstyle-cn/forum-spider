import { Component, Inject } from 'vue-property-decorator';
import { mixins } from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import SpiderService from '@/admin/spider/spider.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class SpiderComponent extends mixins(AlertMixin) {
  @Inject('spiderService') private spiderService: () => SpiderService;
  public error = '';
  public success = '';
  public crawlers: any[] = [];
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'lastModifiedDate';
  public reverse = false;
  public totalItems = 0;

  public mounted(): void {
    this.loadAll();
  }

  public loadAll(): void {
    this.spiderService()
      .retrieve({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .then(res => {
        this.crawlers = res.data;
        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;
      });
  }

  public sort(): any {
    return [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.loadAll();
  }

  public changeOrder(propOrder: string): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }
  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }
}
