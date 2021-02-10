import axios from 'axios';
import Vue from 'vue';
import buildPaginationQueryOpts from '@/shared/sort/sorts';
import {Authority} from '@/shared/security/authority';

export default class SpiderService {
  public get(userId: number): Promise<any> {
    return axios.get(`api/users/${userId}`);
  }

  public crawl(user): Promise<any> {
    return axios.post('api/spider/crawl', user);
  }

  public update(user): Promise<any> {
    return axios.put('api/crawlerDetails', user);
  }

  public retrieve(req?: any): Promise<any> {
    return axios.get(`api/spider/crawl-records?${buildPaginationQueryOpts(req)}`);
  }

  public retrieveDetail(recordId: number, req?: any): Promise<any> {
    return axios.get(`api/spider/crawl-records/${recordId}/details?${buildPaginationQueryOpts(req)}`);
  }

  public getDetailInfo(recordDetailId: number, req?: any): Promise<any> {
    return axios.get(`api/spider/crawl-records/details/${recordDetailId}?${buildPaginationQueryOpts(req)}`);
  }

  public sync(recordId: number) {
    return axios.post(`api/spider/${recordId}/sync`);
  }
}
