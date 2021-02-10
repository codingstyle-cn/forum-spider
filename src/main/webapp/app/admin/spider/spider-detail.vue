<template>
    <div>
        <h2>
            <span id="user-management-page-heading">爬取记录详情</span>
            <router-link tag="button" class="btn btn-info router-link-active float-right" :to="{name: 'SpiderComponent'}">
                <span>返回</span>
            </router-link>

        </h2>
        <div class="table-responsive" v-if="crawlerDetails">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span>ID</span></th>
                    <th v-on:click="changeOrder('author')"><span>作者</span></th>
                    <th v-on:click="changeOrder('subject')"><span>标题</span></th>
                    <th v-on:click="changeOrder('source')"><span>来源</span></th>
                    <th v-on:click="changeOrder('originalUrl')"><span>链接</span></th>
                    <th v-on:click="changeOrder('createdDate')"><span>创建时间</span></th>
                    <th v-on:click="changeOrder('sync')"><span>是否同步</span></th>
                    <th id="modified-date-sort" v-on:click="changeOrder('lastModifiedDate')"><span>最后修改时间</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody v-if="crawlerDetails">
                <tr v-for="crawlerDetail in crawlerDetails" :key="crawlerDetail.id" :id="crawlerDetail.id">
                    <td class="jhi-user-email">{{crawlerDetail.id}}</td>
                    <td>{{crawlerDetail.author}}</td>
                    <td>{{crawlerDetail.subject}}</td>
                    <td>{{crawlerDetail.source}}</td>
                    <td>{{crawlerDetail.originalUrl}}</td>
                    <td>{{crawlerDetail.createdDate | formatDate}}</td>
                    <td>{{crawlerDetail.lastModifiedDate | formatDate}}</td>
                    <td>{{crawlerDetail.sync?'已同步':'未同步'}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'SpiderDetailInfoComponent', params: {detailId: crawlerDetail.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline">查看文章详情</span>
                            </router-link>
                            <button v-if="!crawlerDetail.sync" type="button" class="btn btn-primary" v-on:click="sync(crawlerDetail.id)">
                                <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>同步</span>
                            </button>

                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div v-show="crawlerDetails && crawlerDetails.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./spider-detail.component.ts">
</script>

