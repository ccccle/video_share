import { createApp } from 'vue'
import { createPinia } from 'pinia'
import '@/assets/main.css'
import App from './App.vue'
import router from './router'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import SvgIcon from 'svg-icon'

const app = createApp(App).component('SvgIcon', SvgIcon)

app.use(createPinia().use(piniaPluginPersistedstate))
app.use(router)

app.mount('#app')
