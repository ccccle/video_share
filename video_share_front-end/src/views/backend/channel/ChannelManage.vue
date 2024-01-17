<script setup>
import { ref } from 'vue'
import { channelGetListService, channelDelService } from '@/api/channel.js'
const channelList = ref()
//分页参数
const pageParams = ref({
  page: 1,
  size: 8,
  total: 1
})
const getChannelList = async () => {
  const res = await channelGetListService(
    pageParams.value.page,
    pageParams.value.size
  )
  channelList.value = res.data.data
}
getChannelList()
//删除用户
const handleDelete = async (index, row) => {
  await channelDelService(row.id)
  ElMessage.success('删除成功')
  getChannelList()
}
</script>
<template>
  <div>
    <el-table :data="channelList">
      <el-table-column type="index" label="序号" width="100" />
      <el-table-column property="id" label="分区id" />
      <el-table-column property="channel_name" label="分区名" />
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
  </div>
</template>
