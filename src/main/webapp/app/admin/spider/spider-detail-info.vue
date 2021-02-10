<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="crawlerDetailInfo">
                <h2 class="jh-entity-heading">
                    <span>爬取文章详情</span>
                </h2>
                <dl class="row jh-entity-details">
                    <dt><span>标题</span></dt>
                    <dd>
                        <span>{{crawlerDetailInfo.subject}}</span>
                    </dd>
                    <dt><span>作者</span></dt>
                    <dd>{{crawlerDetailInfo.author}}</dd>
                    <dt><span>来源</span></dt>
                    <dd>{{crawlerDetailInfo.source}}</dd>
                    <dt><span>链接</span></dt>
                    <dd>{{crawlerDetailInfo.originalUrl}}</dd>
                    <dt><span>创建时间</span></dt>
                    <dd> {{crawlerDetailInfo.createdDate | formatDate }}
                    </dd>
                    <dt><span>最后修改时间</span></dt>
                    <dd> {{crawlerDetailInfo.lastModifiedDate | formatDate }}
                    </dd>
                    <dt><span>内容</span></dt>
                    <dd> <p class="card-text">{{crawlerDetailInfo.content}}</p>
                        </dd>
                </dl>
                <router-link tag="button" type="submit"
                             :to="{name: 'SpiderDetailComponent',params: {recordId: crawlerDetailInfo.recordId}}"
                             class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> 返回</span>
                </router-link>
            </div>
        </div>
    </div>

</template>

<script lang="ts" src="./spider-detail-info.component.ts">
</script>

