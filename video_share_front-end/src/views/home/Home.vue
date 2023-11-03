<script setup>
import { ref, watch } from 'vue'
import { videoFeedService } from '@/api/video.js'
import { useRouter, useRoute } from 'vue-router'
import { channelGetListService } from '@/api/channel.js'
const router = useRouter()
const route = useRoute()
// const searchKey = toRef(route.query.searchKey)
// console.log(searchKey)
watch(
  () => route.query.searchKey, // 监听$route对象的变化
  (to, from) => {
    if (to !== from) {
      window.location.reload()
      // 刷新当前路由
    }
  }
)
//分类列表
const channel_list = ref([])
//分页
const page = ref({
  currentPage: 1,
  pageCount: 1
})
const changeCurrent = () => {
  getFeed()
}
//视频列表
const video_list = ref([])
const getFeed = async (channelId) => {
  const searchKey = route.query.searchKey
  const res = await videoFeedService(
    15,
    page.value.currentPage,
    searchKey,
    channelId
  )
  video_list.value = res.data.data.records
  page.value.pageCount = parseInt(res.data.data.pages)
}
getFeed()
//点击视频
const onVideo = (id) => {
  router.push({
    path: '/videoDetail',
    query: { id: id }
  })
}
//获取分区
const getChannel = async () => {
  const res = await channelGetListService()
  channel_list.value = res.data.data
}
getChannel()
</script>
<template>
  <div class="home">
    <!-- 分类 -->
    <div class="button-list">
      <el-button
        class="button"
        v-for="channel in channel_list"
        :key="channel.channel_name"
        @click="getFeed(channel.id)"
        text
        type="primary"
        round
        size="large"
        >{{ channel.channel_name }}</el-button
      >
    </div>
    <!-- 视频 -->
    <div class="video-list">
      <el-empty
        class="empty"
        v-if="!video_list.length"
        description="快上传视频吧！"
      />
      <div class="video-item" v-for="item in video_list" :key="item.id">
        <el-image
          @click="onVideo(item.id)"
          :src="item.video_cover_url"
          class="image"
        ></el-image>
        <a class="video-name" href="">{{ item.video_name }}</a>
        <a class="user-name"
          >{{ item.user.name }}&nbsp;·&nbsp;{{ item.create_time }}</a
        >
      </div>
    </div>
    <div class="pagination">
      <el-pagination
        :background="true"
        :page-size="15"
        :page-count="page.pageCount == 0 ? 1 : page.pageCount"
        v-model:current-page="page.currentPage"
        @current-change="changeCurrent"
        layout="prev, pager, next"
      />
    </div>
  </div>
</template>
<style scoped>
.home {
  position: relative;
  height: 100%;

  .pagination {
    position: absolute;
    left: 45%;
    bottom: 4%;
  }
  .empty {
    position: absolute;
    top: 25%;
    left: 45%;
  }
}
.button-list {
  display: flex;

  .button {
    font-size: 18px;
  }
}
.video-list {
  list-style: none;
  display: flex;
  flex-wrap: wrap;

  .video-item {
    margin: 22px;
    display: flex;
    flex-direction: column;
    width: 314px;
    height: 210px;

    .video-name {
      height: 2em;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      margin: 10px;
      text-decoration: none;
      font-weight: 700;
      font-size: 15px;
      color: rgb(37, 35, 35);
    }
    .user-name {
      height: 2em;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      margin-left: 10px;
      color: rgb(109, 105, 105);
      font-size: 10px;
    }
    .image {
      height: 100%;
      width: 100%;
      border-radius: 10px;
    }
  }
}
</style>
