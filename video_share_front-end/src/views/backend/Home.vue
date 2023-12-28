<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()
</script>
<template>
  <div class="common-layout">
    <el-container>
      <!-- header -->
      <el-header class="header">
        <span><a class="logo" href="/publish/publish">后台管理</a></span>
      </el-header>
      <el-container>
        <!-- aside -->
        <el-aside width="200px" class="aside">
          <el-menu class="menu">
            <el-menu-item @click="router.push('/back/videoBack')">
              <span class="menu-item">
                <SvgIcon name="manuscript"></SvgIcon> 视频管理</span
              >
            </el-menu-item>
            <el-menu-item @click="router.push('/back/userBack')">
              <span class="menu-item"
                ><SvgIcon name="person"></SvgIcon> 用户管理</span
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
      font-size: 24px;
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
