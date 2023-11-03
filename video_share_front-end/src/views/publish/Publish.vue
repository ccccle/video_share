<script setup>
import { useUserStore } from '@/stores/index'
import { ArrowDown, SwitchButton } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
const userStore = useUserStore()
const router = useRouter()
</script>
<template>
  <div class="common-layout">
    <el-container>
      <!-- header -->
      <el-header class="header">
        <span><a class="logo" href="/publish/publish">创作者中心</a></span>
        <el-dropdown trigger="click">
          <span>
            <el-avatar :size="30" :src="userStore.user.avatar" />
            <span class="user-name"
              >{{ userStore.user.name }}<el-icon><ArrowDown /></el-icon
            ></span>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item
                ><el-icon><SwitchButton /></el-icon>退出登录</el-dropdown-item
              >
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <el-container>
        <!-- aside -->
        <el-aside width="200px" class="aside">
          <div class="div-button-publish">
            <el-button
              class="button-publish"
              type="primary"
              size="large"
              @click="router.push('/publish/publish')"
              >发布视频</el-button
            >
          </div>
          <el-menu class="menu">
            <el-menu-item @click="router.push('/publish/videoManage')">
              <span class="menu-item">
                <SvgIcon name="manuscript"></SvgIcon> 稿件管理</span
              >
            </el-menu-item>
            <el-menu-item @click="router.push('/publish/person')">
              <span class="menu-item"
                ><SvgIcon name="person"></SvgIcon> 个人中心</span
              >
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main class="main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<style scoped>
.common-layout {
  background-color: rgb(238, 241, 248);
  height: 100vh;
  .header {
    background-color: white;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 6vh;
    .logo {
      margin-left: 50px;
      text-decoration: none;
      font-size: 20px;
      color: black;
    }
    .user-name {
      color: black;
      margin-right: 20px;
    }
    .quit {
      font-size: 20px;
    }
  }
  .aside {
    background-color: white;
    height: 94vh;
    .menu {
      border-right: none;
      .menu-item {
        line-height: 100%;
        margin: 0 auto;
      }
    }
    .div-button-publish {
      height: 72px;
      display: flex;
      justify-content: center;
      align-items: center;
      border-top: 1px solid #f3f3f3;
      border-bottom: 1px solid #f3f3f3;
    }
  }
}
</style>
