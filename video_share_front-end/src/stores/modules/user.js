import { defineStore } from 'pinia'
import { ref } from 'vue'
//用户模块 token setToken removeToken
export const useUserStore = defineStore(
  'video-user',
  () => {
    const token = ref('')
    const setToken = (obj) => {
      token.value = obj
    }
    const getToken = () => {
      return token.value
    }
    const user = ref({
      id: '',
      email: '',
      name: '',
      avatar: ''
    })
    const setUser = (obj) => {
      user.value = obj
    }
    const getUser = () => {
      return user.value
    }
    return {
      token,
      setToken,
      getToken,
      user,
      setUser,
      getUser
    }
  },
  {
    persist: true
  }
)
