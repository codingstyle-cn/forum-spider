<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" v-if="createCrawlCommand">
                <h2 id="myUserLabel">
                    爬取页面
                </h2>
                <div>
                    <div class="form-group">
                        <label for="exampleTextarea">爬取urls(多个url换行表示):</label>
                        <textarea class="form-control" id="exampleTextarea" rows="3" v-model="createCrawlCommand.urls"></textarea>
                    </div>
                </div>
                <div>
                    <button type="button" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span
                    >取消</span>
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>启动</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
    export default {
        name: "spider-new"
    }
</script>
<script lang="ts" src="./spider-new.component.ts">
</script>

<style scoped>

</style>
