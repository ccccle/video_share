<script setup>
import { rewardPageService,rewardAddService } from '@/api/reward.js'
import { ref } from 'vue';
const formData = ref({
    title: '',
    likeCount: 0,
    price: 0,
    createTime:''
})
const reward = ref('')
const addDialog = ref(false)
const pageParams = ref({
  currentPage: 1,
  pageSize: 15,
  pages: 1
})

function formaData(timer) {
    const year = timer.getFullYear()
    const month = timer.getMonth() + 1 // 由于月份从0开始，因此需加1
    const day = timer.getDate()
    const hour = timer.getHours()
    const minute = timer.getMinutes()
    const second = timer.getSeconds()
    return `${pad(year, 4)}-${pad(month)}-${pad(day)} ${pad(hour)}:${pad(minute)}:${pad(second)}`
}
// 定义具体处理标准
// timeEl 传递过来具体的数值：年月日时分秒
// total 字符串总长度 默认值为2
// str 补充元素 默认值为"0"
function pad(timeEl, total = 2, str = '0') {
    return timeEl.toString().padStart(total, str)
}

const getReward = async() => {
    const res = await rewardPageService(pageParams.value.currentPage, pageParams.value.pageSize)
    reward.value = res.data.data.records
    pageParams.value.pages = Number(res.data.data.pages)  
}
const addReward = async (reward) => {
  addDialog.value = false
  reward.createTime=formaData(reward.createTime)
  await rewardAddService(reward)
  ElMessage.success("添加成功")
  formData.value = {
    title: '',
    likeCount: 0,
    price: 0,
    createTime: ''
  }
  getReward()
}
getReward()
</script>
<template>
    <div class="add-button">
        <el-button type="primary" @click="addDialog=true">添加</el-button>
    </div>
  <div>
    <el-table :data="reward" style="width: 100%">
    <el-table-column type="index" label="序号" width="100" />
    <el-table-column prop="title"  label="活动标题" />
    <el-table-column prop="likeCount"  label="要求获赞数" />
    <el-table-column prop="price" label="奖励金额（分）"/>
    <el-table-column prop="createTime" label="活动开始时间"/>
  </el-table>
  <div class="reward-page">
  <el-pagination
          v-model:current-page="pageParams.currentPage"
          :page-size="8"
          :page-count="pageParams.pages"
          @current-change="getReward()"
          background
          layout="prev, pager, next"
        />
  </div>
</div>
<el-dialog
    v-model="addDialog"
    title="添加活动"
    width="500"
  >
    <el-form :model="formData">
        <el-form-item label="活动标题">
            <el-input v-model="formData.title"></el-input>
        </el-form-item>
        <el-form-item label="要求获赞数">
            <el-input-number :min=0 v-model="formData.likeCount"></el-input-number>
        </el-form-item>
        <el-form-item label="奖励金额（分）">
            <el-input-number :min=0 v-model="formData.price"></el-input-number>
        </el-form-item>
        <el-form-item label="活动开始时间">
            <el-date-picker
        v-model="formData.createTime"
        type="datetime"
        placeholder="选择活动开始时间"
      />
        </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="addDialog = false">取消</el-button>
        <el-button type="primary" @click="addReward(formData)">
          添加
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>
<style scoped>
.add-button{
    margin-bottom: 10px;
}
.reward-page{
    margin: 20px;
    display: flex;
    justify-content: center;
}
</style>