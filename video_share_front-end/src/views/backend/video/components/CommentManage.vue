<script setup>
import { ref, defineExpose } from 'vue'
import { commentGetService, commentDel } from '@/api/comment.js'
const dialogVisible = ref(false)
const openDialog = (id) => {
  dialogVisible.value = true
  pageParams.value.id = id
  getCommentList()
}
const commentList = ref()
defineExpose({ openDialog })
//分页参数
const pageParams = ref({
  page: 1,
  size: 12,
  total: 1,
  id: 0
})
//分页查询评论
const getCommentList = async () => {
  const res = await commentGetService(
    pageParams.value.id,
    pageParams.value.page,
    pageParams.value.size
  )
  commentList.value = res.data.data.records
  pageParams.value.total = res.data.data.total
}
const handleDelete = async (index, row) => {
  console.log(row)
  await commentDel(row.id)
  await getCommentList()
  ElMessage.success('删除成功')
}
</script>

<template>
  <div>
    <el-drawer v-model="dialogVisible" direction="rtl" size="60%">
      <el-table :data="commentList">
        <el-table-column type="index" label="序号" width="100" />
        <el-table-column property="commentUserId" label="发送人id" />
        <el-table-column property="text" label="评论" width="200" />
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
      <el-pagination
        layout="prev, pager, next"
        :total="Number(pageParams.total)"
        v-model:current-page="pageParams.page"
        @current-change="changeCurrent"
      />
    </el-drawer>
  </div>
</template>
<style scoped></style>
