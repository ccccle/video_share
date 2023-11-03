<script setup>
import { useUserStore } from '@/stores/index.js'
import { ref } from 'vue'
import { userUpdateService, userGetInfoService } from '@/api/user.js'
const userStore = useUserStore()
const user = ref(userStore.user)
const avatarUrl = ref(user.value.avatar)
//图片文件转换成url访问
const onImageFile = (uploadFile) => {
  avatarUrl.value = URL.createObjectURL(uploadFile.raw)
  user.value.avatar_data = uploadFile.raw
}
//上传
const upLoad = async () => {
  await userUpdateService(user.value)
  ElMessage.success('修改成功')
  //修改成功后重新获取
  const res = await userGetInfoService(user.value.id)
  userStore.setUser(res.data.data)
}
</script>
<template>
  <div class="person">
    <div class="header"><span class="title">个人中心</span></div>
    <el-form>
      <el-form-item class="avarar-item">
        <el-upload
          :show-file-list="false"
          :auto-upload="false"
          :on-change="onImageFile"
          class="avatar"
        >
          <el-avatar :size="100" :src="avatarUrl" />
        </el-upload>
      </el-form-item>
      <el-form-item class="name-item" label="昵称：">
        <el-input v-model="user.name"></el-input>
      </el-form-item>
      <el-form-item class="button-item">
        <el-button @click="upLoad" type="primary" round>修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<style scoped>
.person {
  position: relative;
  top: 5%;
  left: 15%;
  padding: 30px;
  height: 80%;
  width: 70%;
  background-color: white;
  border-radius: 20px;
  .title {
    font-size: 20px;
  }
  .avarar-item {
    margin: 50px;
    .avatar {
      margin: 0 auto;
    }
  }
  .name-item {
    width: 40%;
    margin: 0 auto;
  }
  .button-item {
    width: 6%;
    margin: 0 auto;
    margin-top: 30px;
  }
}
</style>
