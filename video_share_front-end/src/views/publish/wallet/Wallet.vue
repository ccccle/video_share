<script setup>
import { rewardHistoryPageService } from '@/api/rewardHistory.js'
import { walletGetService,createWalletService } from '@/api/wallet.js'
import { ref } from 'vue';
const pageParams = ref({
  currentPage: 1,
  pageSize: 8,
  pages: 1
})
const wallet = ref('')
const rewardHistory = ref('')

const getWallet = async () => {
  const res = await walletGetService()
  wallet.value = res.data.data
  console.log(res.data);
  if (res.data.code === 500) {
    open()
  } else {
  const res1 = await rewardHistoryPageService(pageParams.value.currentPage, pageParams.value.pageSize)
  rewardHistory.value = res1.data.data.records
  pageParams.value.pages = Number(res1.data.data.pages) 
  }
}
//弹出是否开通创作奖励
const open = () => {
  ElMessageBox.confirm(
    `暂时未开通创作奖励，无法获得奖励。是否开通创作奖励？`,
    {
      confirmButtonText: '开通',
      cancelButtonText: '取消',
    }
  )
    .then(() => {
      createWalletService()
      ElMessage({
        type: 'success',
        message: '开通成功',
      })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消开通',
      })
    })
}
getWallet()
</script>
<template>
  <span class="wallet">
    <p v-if="wallet">钱包余额：{{ wallet.balance/100 }} 元</p>
    <!-- 可以提供一个备用内容，以防wallet不存在 -->
    <p v-else>钱包信息不可用</p>
  </span>
  <div>
    <el-table :data="rewardHistory" style="width: 100%">
    <el-table-column type="index" label="序号" width="100" />
    <el-table-column prop="videoVo.video_name"  label="视频标题" />
    <el-table-column prop="videoVo.video_cover_url" label="视频封面">
        <template #default="scope">
          <div
            style="
              display: flex;
              align-items: center;
              height: 70px;
              width: 70px;
            "
          >
            <el-image :fit="contain" :src="scope.row.videoVo.video_cover_url" />
          </div>
        </template>
      </el-table-column>
    <el-table-column prop="reward.title" label="奖励名称"  />
    <el-table-column prop="reward.price"  label="奖励金额(元)" >
      <template #default="scope">
        {{ scope.row.reward.price/100 }}
      </template>
    </el-table-column>
    <el-table-column prop="createTime" label="获得时间" />
  </el-table>
  <div class="reward-page">
  <el-pagination
          v-model:current-page="pageParams.currentPage"
          :page-size="8"
          :page-count="pageParams.pages"
          @current-change="getWallet()"
          background
          layout="prev, pager, next"
        />
  </div>
</div>
</template>
<style scoped>
.reward-page{
      margin: 20px;
      display: flex;
      justify-content: center;
}
.wallet{
  margin-bottom: 10px;
  font-size: 24px;
  font-weight: 400;
}
</style>