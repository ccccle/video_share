import axios from 'axios'
import { useUserStore } from '@/stores'
import { ElMessage } from 'element-plus'
import router from '@/router'

const baseURL = 'http://localhost:8080'
const instance = axios.create({
  baseURL,
  timeout: 600000
})
//请求拦截
instance.interceptors.request.use(
  (config) => {
    const useStore = useUserStore()
    if (useStore.token) {
      config.headers.Authorization = useStore.token
    }
    return config
  },
  (err) => Promise.reject(err)
)
//响应拦截
// instance.interceptors.response.use(
//   (res) => {
//     if (res.data.code === 0) {
//       return res
//     }
//     //处理业务失败，给错误提示，抛出错误
//     ElMessage.error(res.data.message || '服务异常 ')
//     return Promise.reject(res.data)
//   },
//   (err) => {
//     //错误的特殊情况
//     if (err.response.status === 401) {
//       router.push('/')
//     }
//     //错误的默认情况
//     ElMessage.error(err.response.data.message || '服务异常 ')
//     return Promise.reject(err)
//   }
// )

export default instance
export { baseURL }
