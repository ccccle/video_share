import { createApp } from 'vue'
import '@/assets/main.css'
import '@/assets/danmuku.scss'
import App from './App.vue'
import router from './router'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import SvgIcon from 'svg-icon'
import pinia from '@/stores/index'
const app = createApp(App).component('SvgIcon', SvgIcon)
app.use(router)
app.use(pinia.use(piniaPluginPersistedstate))

app.mount('#app')
