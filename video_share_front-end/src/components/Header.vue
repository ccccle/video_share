<script setup>
import { useRouter } from 'vue-router'
import { Search } from '@element-plus/icons-vue'
import { ref } from 'vue'
const router = useRouter()
// 搜索
const searchKey = ref('')
const search = () => {
  router.push({
    path: '/',
    query: {
      searchKey: searchKey.value
    }
  })
}
</script>
<template>
  <el-row>
    <!-- LOGO -->
    <el-col class="logo" :span="1" :offset="1">
      <el-link href="/" :underline="false">LOGO</el-link>
    </el-col>
    <!-- 搜索 -->
    <el-col :span="6" :offset="7"
      ><el-input
        v-model="searchKey"
        @change="search"
        class="w-50 m-2"
        placeholder="探索更多内容"
        :suffix-icon="Search"
    /></el-col>
    <!-- 创作中心 -->
    <el-col :span="3" :offset="6">
      <el-button @click="router.push('/publish')" round
        >创作中心</el-button
      ></el-col
    >
  </el-row>
</template>
