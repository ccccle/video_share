<script setup>
import { ref, watch } from 'vue'
import { userGetInfoService } from '@/api/user.js'
import { fanGetCountService } from '@/api/fan.js'
import { useRoute, useRouter } from 'vue-router'
const route = useRoute()
const router = useRouter()
const userCount = ref({
  followCount: 0,
  fanCount: 0
})
const userInfo = ref({})
watch(
  () => route.query.userId, // 监听$route对象的变化
  (to, from) => {
    if (to !== from) {
      window.location.reload()
      // 刷新当前路由
    }
  }
)
const getCount = async () => {
  const followRes = await fanGetCountService(route.query.userId, 'follow')
  userCount.value.followCount = followRes.data.data
  const fanRes = await fanGetCountService(route.query.userId, 'fan')
  userCount.value.fanCount = fanRes.data.data
}
getCount()
//跳转类型
const type = ref('')
const getUserInfo = async () => {
  const res = await userGetInfoService(route.query.userId)
  userInfo.value = res.data.data
}
//跳转到视频
const toVideo = () => {
  router.push({
    path: '/userInfo/video',
    query: {
      userId: route.query.userId
    }
  })
  type.value = 'video'
}
//跳转到关注
const toFollow = () => {
  router.push({
    path: '/userInfo/follow',
    query: {
      userId: route.query.userId
    }
  })
  type.value = 'follow'
}
//跳转到粉丝
const toFan = () => {
  router.push({
    path: '/userInfo/fan',
    query: {
      userId: route.query.userId
    }
  })
  type.value = 'fan'
}
getUserInfo()
</script>
<template>
  <div class="userInfo">
    <div class="user">
      <div class="avatar">
        <el-avatar :size="130" :src="userInfo.avatar" />
      </div>
      <div class="username-useraction">
        <div class="username">{{ userInfo.name }}</div>
        <div class="useraction">
          <div class="follow">
            <div class="follow-count">{{ userCount.followCount }}</div>
            <div class="follow-show">关注</div>
          </div>
          <div class="fan">
            <div class="fan-count">{{ userCount.fanCount }}</div>
            <div class="fan-show">粉丝</div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="action-button">
    <span class="button-list">
      <el-button text round @click="toVideo"
        ><span class="button-font">视频</span></el-button
      >
      <el-button text round @click="toFollow"
        ><span class="button-font">关注</span></el-button
      >
      <el-button text round @click="toFan"
        ><span class="button-font">粉丝</span></el-button
      >
    </span>
  </div>
  <div class="bottom-line"></div>
  <div>
    <router-view :userId="route.query.userId" :type="type"></router-view>
  </div>
</template>
<style scoped>
.userInfo {
  display: flex;
  justify-content: center;
  .user {
    display: flex;
    .username-useraction {
      margin-left: 100px;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      .username {
        color: #333333;
        font-size: 24px;
      }
      .useraction {
        display: flex;
        .follow {
          display: flex;
          margin-right: 20px;
          color: #33333399;
          font-size: 14px;
          .follow-count {
            margin-right: 5px;
            color: black;
          }
        }
        .fan {
          display: flex;
          color: #33333399;
          font-size: 14px;
          .fan-count {
            margin-right: 5px;
            color: black;
          }
        }
      }
    }
  }
}
.action-button {
  margin: 100px auto 0;
  display: flex;
  justify-content: center;
  .button-font {
    font-size: 16px;
    color: #333333cc;
  }
}
.bottom-line {
  width: 100%;
  margin-top: 10px;
  border-bottom: solid 1px rgb(235, 235, 235);
}
</style>
