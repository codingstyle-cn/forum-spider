<template>
    <div>
        <h2>
            <span id="user-management-page-heading">爬取文章记录</span>
            <router-link tag="button" class="btn btn-primary float-right jh-create-entity" :to="{name: 'SpiderNewComponent'}">
                <font-awesome-icon icon="plus"></font-awesome-icon> <span>爬取文章</span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
                 dismissible
                 :variant="alertType"
                 @dismissed="dismissCountDown=0"
                 @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <div class="table-responsive" v-if="crawlers">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('email')"><span>爬取链接</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('login')"><span>爬取条数</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'login'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdDate')"><span>创建时间</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdDate'"></jhi-sort-indicator></th>
                    <th id="modified-date-sort" v-on:click="changeOrder('lastModifiedDate')"><span>最后修改时间</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastModifiedDate'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody v-if ="crawlers">
                <tr v-for="crawler in crawlers" :key="crawler.id" :id="crawler.login">
                    <td><router-link tag="a" :to="{name: 'JhiUserView', params: {userId: crawler.login}}">{{crawler.id}}</router-link></td>
                    <td class="jhi-user-email">{{crawler.crawlUrls}}</td>
                    <td>{{crawler.count}}</td>
                    <td>{{crawler.createdDate | formatDate}}</td>
                    <td>{{crawler.lastModifiedDate | formatDate}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'SpiderDetailComponent', params: {recordId: crawler.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline">查看详情</span>
                            </router-link>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            <b-modal ref="removeUser" id="removeUser" title="Confirm delete operation"  @ok="deleteUser()">
                <div class="modal-body">
                    <p id="jhi-delete-user-heading">Are you sure you want to delete this user?</p>
                </div>
                <div slot="modal-footer">
                    <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
                    <button type="button" class="btn btn-primary" id="confirm-delete-user" v-on:click="deleteUser()">Delete</button>
                </div>
            </b-modal>
        </div>
        <div v-show="crawlers && crawlers.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>


</template>

<script>
    export default {
        name: "spider"
    }
</script>

<script lang="ts" src="./spider.component.ts">
</script>

