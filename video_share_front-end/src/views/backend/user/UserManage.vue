<script setup>
import { ref } from 'vue'
import { userGetPage, userDel } from '@/api/user.js'
const userList = ref()
//分页参数
const pageParams = ref({
  page: 1,
  size: 8,
  total: 1
})
const getUserList = async () => {
  const res = await userGetPage(pageParams.value.page, pageParams.value.size)
  userList.value = res.data.data.records
}
getUserList()
//删除用户
const handleDelete = async (index, row) => {
  await userDel(row.id)
  ElMessage.success('删除成功')
  getUserList()
}
</script>
<template>
  <div>
    <el-table :data="userList">
      <el-table-column type="index" label="序号" width="100" />
      <el-table-column property="id" label="用户id" />
      <el-table-column property="email" label="邮箱" />
      <el-table-column property="name" label="用户名" />
      <el-table-column property="avatar" label="头像">
        <template #default="scope">
          <div
            style="
              display: flex;
              align-items: center;
              height: 70px;
              width: 70px;
            "
          >
            <el-image :fit="contain" :src="scope.row.avatar" />
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="user-page"
      layout="prev, pager, next"
      :total="Number(pageParams.total)"
      v-model:current-page="pageParams.page"
      background
      @current-change="changeCurrent"
    />
  </div>
</template>
<style scoped>
.user-page{
      margin: 20px;
      display: flex;
      justify-content: center;
}
</style>
