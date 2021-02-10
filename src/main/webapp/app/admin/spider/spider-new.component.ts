import { Component, Inject, Vue } from 'vue-property-decorator';
import AlertService from '@/shared/alert/alert.service';
import SpiderService from '@/admin/spider/spider.service';
import { CreateCrawlCommand, ICreateCrawlCommand } from '@/shared/model/CreateCrawlCommand';

const validations: any = {};

@Component({
  validations,
})
export default class SpiderNewComponent extends Vue {
  @Inject('spiderService') private spiderService: () => SpiderService;
  @Inject('alertService') private alertService: () => AlertService;
  public createCrawlCommand: ICreateCrawlCommand;
  public isSaving = false;
  public authorities: any[] = [];
  public languages: any = this.$store.getters.languages;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initAuthorities();
      if (to.params.userId) {
        vm.init(to.params.userId);
      }
    });
  }

  public constructor() {
    super();
    this.createCrawlCommand = new CreateCrawlCommand();
  }

  public initAuthorities() {}

  public save(): void {
    this.isSaving = true;
    this.spiderService()
      .crawl(this.createCrawlCommand)
      .then(res => {
        this.returnToList();
        this.alertService().showAlert(this.getMessageFromHeader(res), 'success');
      });
  }

  public previousState(): void {
    (<any>this).$router.go(-1);
  }

  private returnToList(): void {
    this.isSaving = false;
    (<any>this).$router.go(-1);
  }

  private getMessageFromHeader(res: any): any {
    return res.headers['x-spiderapp-alert'];
  }
}
