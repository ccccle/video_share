<script setup>
import { House, Bell, Position } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useUserStore } from '@/stores/index.js'
import Header from '@/components/Header.vue'
import LoginDialog from '@/components/LoginDialog.vue'
import { useRouter } from 'vue-router'
const router = useRouter()

//登录
const loginDialog = ref()
const onLogin = () => {
  loginDialog.value.open()
}
const userStore = useUserStore()
//退出登录
const quitLogin = () => {
  userStore.setToken('')
  userStore.setUser({})
}
</script>
<template>
  <el-container class="layout-container">
    <el-header height="100px" class="header"> <Header></Header> </el-header>
    <el-container>
      <!-- 左侧 -->
      <el-aside class="aside">
        <el-menu class="menu">
          <el-menu-item @click="router.push('/')">
            <el-icon class="icon-style"><House /></el-icon>
            <span>主页</span>
          </el-menu-item>
          <el-menu-item @click="router.push('/publish/publish')">
            <el-icon class="icon-style"><Position /></el-icon>
            <span>发布</span>
          </el-menu-item>
          <el-menu-item @click="router.push('/notice')">
            <el-icon class="icon-style"><Bell /></el-icon>
            <span>通知</span>
          </el-menu-item>

          <el-menu-item
            @click="
              router.push({
                path: '/userInfo',
                query: {
                  userId: userStore.user.id
                }
              })
            "
            v-if="userStore.token"
          >
            <el-avatar :size="30" :src="userStore.user.avatar" />
            <span style="margin-left: 10px">我</span>
          </el-menu-item>

          <el-button
            v-else
            class="login-button"
            type="primary"
            round
            auto-insert-space
            size="large"
            @click="onLogin"
            >登录</el-button
          >
        </el-menu>
        <el-button
          @click="quitLogin"
          v-if="userStore.token"
          round
          type="primary"
          >退出登录</el-button
        >
      </el-aside>
      <!-- 主体 -->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
    <LoginDialog ref="loginDialog" style="margin: 15% auto"></LoginDialog>
  </el-container>
</template>
<style scoped>
.layout-container {
  height: 100vh;
  .header {
    line-height: 100px;
  }
  .aside {
    padding: 40px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    .menu {
      border-right: none;
      .login-button {
        width: 90%;
      }
    }
  }
}
</style>
