<script setup>
import { ref, defineProps, onMounted } from 'vue'
import { fanFollowService, fanGetFollowService } from '@/api/fan.js'
import { useUserStore } from '@/stores/index.js'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
const userStore = useUserStore()
const router = useRouter()
const { userId } = props
const props = defineProps({
  userId: {
    type: String
  },
  type: {
    type: String
  }
})
//查询和登录者的关注情况
const loginFollowList = ref([])
const getLoginFollowList = () => {
  fanGetFollowService(userStore.user.id, 'follow').then((res) => {
    if (res.status === 200) {
      loginFollowList.value = res.data.data
      load()
    } else {
      ElMessage.warning(res.data.message)
    }
  })
}
onMounted(() => {
  getLoginFollowList()
})
//关注
const pageParams = ref({
  page: 0,
  size: 10
})
const followList = ref([])
//提取出fanId
const equalsFans = ref([])
const getFollowList = async () => {
  const res = await fanGetFollowService(
    userId,
    'fan',
    pageParams.value.page,
    pageParams.value.size
  )
  followList.value = followList.value.concat(res.data.data.records)
  const fan1Ids = followList.value.map((item) => item.user.id)
  const fan2Ids = loginFollowList.value.map((item) => item.fan.id)
  equalsFans.value = fan2Ids.filter((item) => fan1Ids.includes(item))
}
const load = () => {
  pageParams.value.page++
  getFollowList()
}
//关注

const onFan = async (fan) => {
  let loginFan = {
    user_id: userStore.user.id,
    fan_id: fan.id,
    status: equalsFans.value.includes(fan.id) ? 0 : 1
  }
  await fanFollowService(loginFan)
  if (!equalsFans.value.includes(fan.id)) {
    equalsFans.value.push(fan.id)
  } else {
    equalsFans.value = equalsFans.value.filter((item) => item !== fan.id)
  }
}
//跳转个人信息
const toUserInfo = (userId) => {
  router.push({
    path: '/userInfo',
    query: {
      userId
    }
  })
}
</script>
<template>
  <ul
    v-infinite-scroll="load"
    :infinite-scroll-immediate="false"
    class="infinite-list"
    style="overflow: auto"
  >
    <li
      v-for="follow in followList"
      :key="follow.id"
      class="infinite-list-item"
    >
      <span @click="toUserInfo(follow.user.id)" class="cover">
        <el-avatar class="image" :src="follow.user.avatar"></el-avatar>
      </span>
      <span class="name">{{ follow.user.name }}</span>
      <span class="button">
        <el-button
          type="info"
          @click="onFan(follow.user)"
          round
          v-if="equalsFans.includes(follow.user.id)"
        >
          已关注
        </el-button>
        <el-button type="primary" round @click="onFan(follow.user)" v-else>
          +关注
        </el-button>
      </span>
    </li>
  </ul>
</template>
<style scoped>
.infinite-list {
  /* height: 450px; */
  height: 100%;
  padding: 0;
  list-style: none;
  display: flex;
  flex-wrap: wrap;

  .infinite-list-item {
    margin-top: 20px;
    margin-left: 20px;
    margin-right: 40px;
    width: 40%;
    height: 14%;
    display: flex;
    align-items: center;

    /* .cover {
      flex: 0 0 auto;
    } */
    .name {
      margin-left: 20px;
      flex: 1 1 auto;
      font-size: 16px;
      color: #333;
    }

    /* .button {
      flex: 0 0 auto;
    } */
  }
}
</style>
