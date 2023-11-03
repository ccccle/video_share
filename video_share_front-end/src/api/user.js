import request from '@/utils/request'
//发送验证码
export const userSendCodeService = (email) => {
  return request.get('/api/v1/user/sendCode', {
    params: { email }
  })
}
//用户登录
export const userLoginService = ({ email, code }) => {
  return request.post('/api/v1/user/login', { email, code })
}
//更改用户信息
export const userUpdateService = (user) => {
  console.log(user)
  var formData = new FormData()
  for (let key in user) {
    formData.append(key, user[key])
  }
  return request.put('/api/v1/user/updateUserInfo', formData, {
    headers: {
      'Content-Type': 'multipart/form-data' // 设置请求头为 multipart/form-data
    }
  })
}
//获取个人用户信息
export const userGetInfoService = (id) => {
  return request.get('/api/v1/user/getUserInfo', {
    params: { id }
  })
}
