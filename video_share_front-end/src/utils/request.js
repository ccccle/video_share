import axios from 'axios'
import { useUserStore } from '@/stores'


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
export default instance
export { baseURL }
