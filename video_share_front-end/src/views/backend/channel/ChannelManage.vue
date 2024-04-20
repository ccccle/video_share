<script setup>
import { ref } from 'vue'
import { channelGetListService, channelDelService,channelAddService } from '@/api/channel.js'
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
const addDialog = ref(false)
const channelName = ref("123")
const addChannel = async (channelName) => {
  await channelAddService(channelName)
  ElMessage.success('添加成功')
  getChannelList()
  addDialog.value = false
  channelName.value=""
}
</script>
<template>
  <el-dialog
    v-model="addDialog"
    title="添加分区"
    width="500"
  >
  <template #footer>
    <el-input v-model="channelName" placeholder="输入分区名称" style="margin-bottom: 80px"></el-input>
      <div class="dialog-footer">
        <el-button @click="addDialog = false">取消</el-button>
        <el-button type="primary" @click="addChannel(channelName)">
          添加
        </el-button>
      </div>
    </template>
  </el-dialog>
  <div class="add-button">
        <el-button type="primary" @click="addDialog=true">添加</el-button>
    </div>
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
    <el-pagination class="channel-page"
      layout="prev, pager, next"
      :total="Number(pageParams.total)"
      v-model:current-page="pageParams.page"
      @current-change="changeCurrent"
      background
    />
  </div>
</template>

<style scoped>
.channel-page{
      margin: 20px;
      display: flex;
      justify-content: center;
}
</style>
