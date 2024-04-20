<script setup>
import { ref, watch } from 'vue'
import { Message, Lock } from '@element-plus/icons-vue'
import { userSendCodeService, userLoginService } from '@/api/user.js'
import { useUserStore } from '@/stores/index'
import { useRouter } from 'vue-router'
// 开关登录窗口
const dialogVisible = ref(false)
const open = () => {
  dialogVisible.value = true
}
const userForm = ref({
  email: '',
  code: ''
})
//发送验证码
const time = ref(0)
var timeOut
const sendCode = async () => {
  //设置验证码时间
  time.value = 60
  timeOut = setInterval(function () {
    time.value--
  }, 1000)
  await userSendCodeService(userForm.value.email)
  ElMessage.success('发送成功')
}
//监听时间清除定时器
watch(time, async (newValue) => {
  if (newValue <= 0) clearInterval(timeOut)
})
// 注册登录
const userStore = useUserStore()
const router = useRouter()
const loginForm = ref()
const login = async () => {
  await loginForm.value.validate()
  const res = await userLoginService(userForm.value)
  console.log(res.data)
  if (res.data.code === 1) ElMessage.success(res.data.message)
  else ElMessage.error(res.data.message)
  userStore.setToken(res.data.data.token)
  userStore.setUser(res.data.data)
  if (userForm.value.email === '1175414205@qq.com') {
    router.push('/back')
  }
  else {
      router.push('/')
  }
  dialogVisible.value = false
  //注册验证
}
const loginRules = {
  email: [
    { required: true, message: '邮箱不能为空', trigger: 'blur' },
    {
      pattern: /^([a-zA-Z\d][\w-]{2,})@(\w{2,})\.([a-z]{2,})(\.[a-z]{2,})?$/,
      message: '请输入正确的邮邮箱',
      trigger: 'blur'
    }
  ],
  code: [
    { required: true, message: '验证码不能为空', trigger: 'blur' },
    {
      min: 4,
      max: 4,
      message: '验证码为4位',
      trigger: 'blur'
    }
  ]
}

defineExpose({
  open
})
</script>
<template>
  <el-dialog
    style="padding: 30px"
    v-model="dialogVisible"
    width="30%"
    class="dialog"
    ><template #header>
      <span class="title">邮箱登录</span>
    </template>

    <el-form :rules="loginRules" ref="loginForm" :model="userForm">
      <el-form-item prop="email">
        <el-input
          v-model="userForm.email"
          placeholder="请输入邮箱"
          :prefix-icon="Message"
        ></el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input
          v-model="userForm.code"
          placeholder="请输入验证码"
          :prefix-icon="Lock"
          prop="code"
          ><template #append>
            <el-button v-if="time <= 0" @click="sendCode">发送验证码</el-button>
            <el-button v-else type="info" plain disabled
              >请在{{ time }}秒后重试</el-button
            >
          </template></el-input
        >
      </el-form-item>
      <el-form-item>
        <el-button @click="login" round type="primary" class="login-button"
          >登录/注册</el-button
        >
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<style lang="less" scoped>
.title {
  font-size: 18px;
  color: var(--color-secondary-label);
  font-weight: 600;
}
.login-button {
  margin: 0 auto;
  width: 60%;
  height: 40px;
}
</style>
