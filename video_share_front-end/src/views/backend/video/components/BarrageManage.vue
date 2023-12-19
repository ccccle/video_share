<script setup>
import { ref } from 'vue'
import { barrageGetList, barrageDel } from '@/api/barrage.js'
const dialogVisible = ref(false)
const openDialog = (id) => {
  dialogVisible.value = true
  pageParams.value.id = id
  getBarrageList()
}
const barrageList = ref([])
//分页参数
const pageParams = ref({
  page: 1,
  size: 12,
  total: 1,
  id: 0
})
//分页查询弹幕
const getBarrageList = async () => {
  const res = await barrageGetList(
    pageParams.value.page,
    pageParams.value.size,
    pageParams.value.id
  )
  barrageList.value = res.data.data.records
  pageParams.value.total = res.data.data.total
}
const changeCurrent = () => {
  getBarrageList()
}
//删除弹幕
const handleDelete = async (index, row) => {
  await barrageDel(row.barrage_id)
  await getBarrageList()
  ElMessage.success('删除成功')
}
defineExpose({ openDialog })
</script>

<template>
  <el-drawer v-model="dialogVisible" direction="rtl" size="60%">
    <el-table :data="barrageList">
      <el-table-column type="index" label="序号" width="100" />
      <el-table-column property="user_id" label="发送人" />
      <el-table-column property="time" label="弹幕出现时间(秒)" width="150" />
      <el-table-column property="text" label="弹幕内容" width="200" />
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
</template>
<style scoped></style>
