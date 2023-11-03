<script setup>
import { videoUserService } from '@/api/video.js'
import { useUserStore } from '@/stores/index.js'
import { ref } from 'vue'
import { videoDelSerivce } from '@/api/video.js'
import { useRouter } from 'vue-router'
const router = useRouter()
const userStore = useUserStore()
const videoList = ref([])
const pageParams = ref({
  currentPage: 1,
  pageSize: 5,
  pages: 1
})
const getVideoList = async () => {
  const res = await videoUserService(
    pageParams.value.currentPage,
    pageParams.value.pageSize,
    userStore.user.id
  )
  videoList.value = res.data.data.records
  pageParams.value.pages = parseInt(res.data.data.pages)
}
const changeCurrent = () => {
  getVideoList()
}
const delVideo = async (item) => {
  await videoDelSerivce(item.id)
  ElMessage.success('删除成功')
  getVideoList()
}
getVideoList()
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
  <div class="background">
    <div class="header"><span class="title">我的投稿</span></div>
    <div class="video-list">
      <el-empty
        v-if="videoList.length === 0"
        style="height: 80%"
        description="快点上传视频吧！"
      />
      <div class="video" v-for="item in videoList" :key="item.id">
        <div @click="toVideoDetail(item.id)" class="video-cover">
          <el-image class="cover-img" :src="item.video_cover_url"></el-image>
        </div>
        <div class="video-information">
          <div @click="toVideoDetail(item.id)" class="video-name">
            {{ item.video_name }}
          </div>
          <div class="video-time">{{ item.create_time }}</div>
          <div class="video-count">
            <div class="play-count">
              <SvgIcon
                name="play"
                size="12"
                color="rgb(151, 156, 160)"
              ></SvgIcon
              >{{ item.play_count }}
            </div>
            <div class="like-count">
              <SvgIcon
                name="like"
                size="12"
                color="rgb(151, 156, 160)"
              ></SvgIcon
              >{{ item.like_count }}
            </div>
            <div class="comment-count">
              <SvgIcon name="comment" size="12" color="#99a2aa"></SvgIcon
              >{{ item.comment_count }}
            </div>
          </div>
        </div>
        <div class="video-action">
          <el-button @click="delVideo(item)" type="danger">删除</el-button>
        </div>
      </div>
      <div class="video-page">
        <el-pagination
          v-model:current-page="pageParams.currentPage"
          :page-size="5"
          :page-count="pageParams.pages"
          @current-change="changeCurrent"
          background
          layout="prev, pager, next"
        />
      </div>
    </div>
  </div>
</template>
<style scoped>
.background {
  margin: 0 auto;
  padding: 30px;
  height: 90%;
  width: 60%;
  background-color: white;
  border-radius: 30px;
  .title {
    font-size: 20px;
  }
  .video-list {
    height: 100%;
    .video {
      display: flex;
      justify-content: space-around;
      margin-top: 20px;
      .video-cover {
        display: flex;
        height: 20%;
        width: 20%;
        .cover-img {
        }
      }
      .video-information {
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        width: 60%;
        .video-name {
          text-overflow: -o-ellipsis-lastline;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          line-clamp: 2;
          -webkit-box-orient: vertical;
          color: #212121;
          font-size: 16px;
        }
        .video-time {
          color: #505050;
          font-size: 14px;
        }
        .video-count {
          width: 40%;
          display: flex;
          justify-content: space-between;
          color: #99a2aa;
          font-size: 12px;
        }
      }
      .video-action {
        display: flex;
        align-items: center;
      }
    }
    .video-page {
      margin: 50px;
      display: flex;
      justify-content: center;
    }
  }
}
</style>
