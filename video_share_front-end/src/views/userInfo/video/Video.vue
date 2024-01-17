<script setup>
import { ref, defineProps } from 'vue'
import { videoUserService } from '@/api/video.js'
import { useRouter } from 'vue-router'
const router = useRouter()
const { userId } = defineProps({
  userId: {
    type: String
  }
})
//分页
const pageParams = ref({
  page: 1,
  size: 6
})
//视频信息
const videoList = ref([])
const getVideoList = async () => {
  const res = await videoUserService(
    pageParams.value.page,
    pageParams.value.size,
    userId
  )
  videoList.value = videoList.value.concat(res.data.data.records)
}
getVideoList()
const load = () => {
  pageParams.value.page++
  getVideoList()
}
//跳转视频详细
const toVideoDetail = (id) => {
  router.push({
    path: '/videoDetail',
    query: {
      id
    }
  })
}
</script>
<template>
  <ul
    v-infinite-scroll="load"
    :infinite-scroll-immediate="false"
    class="infinite-list"
  >
    <li v-for="video in videoList" :key="video.id" class="infinite-list-item">
      <div @click="toVideoDetail(video.id)" class="cover">
        <el-image class="image" :src="video.video_cover_url"></el-image>
      </div>
      <div @click="toVideoDetail(video.id)" class="name">
        {{ video.video_name }}
      </div>
    </li>
  </ul>
</template>

<style scoped>
.infinite-list {
  overflow: auto;
  height: 450px;
  /* width: 100%; */
  padding: 0;
  list-style: none;
  display: flex;
  flex-wrap: wrap;

  .infinite-list-item {
    width: 25%;
    margin-left: 80px;
    margin-top: 20px;
    .cover {
      .image {
        border-radius: 30px;
        aspect-ratio: 16 / 9;
        width: 100%;
      }
    }
    .name {
      margin-top: 8px;
      margin-left: 18px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      font-size: 14px;
      color: #333;
    }
  }
}
</style>
