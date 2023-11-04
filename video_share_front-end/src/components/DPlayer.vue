<template>
  <div ref="videoRef"></div>
</template>

<script setup>
import DPlayer from 'dplayer'

import { ref, onBeforeUnmount, onMounted } from 'vue'

const videoRef = ref()
const state = ref({
  instance: null
})

const props = defineProps({
  // 是否自动播放
  autoplay: {
    type: Boolean,
    default: false
  },
  // 主题色
  theme: {
    type: String,
    default: '#0093ff'
  },
  // 视频是否循环播放
  loop: {
    type: Boolean,
    default: true
  },
  // 语言(可选值: 'en', 'zh-cn', 'zh-tw')
  lang: {
    type: String,
    default: 'zh-cn'
  },
  // 是否开启截图(如果开启，视频和视频封面需要允许跨域)
  screenshot: {
    type: Boolean,
    default: false
  },
  // 是否开启热键
  hotkey: {
    type: Boolean,
    default: true
  },
  // 视频是否预加载(可选值: 'none', 'metadata', 'auto')
  preload: {
    type: String,
    default: 'auto'
  },
  // 默认音量
  volume: {
    type: Number,
    default: 0.7
  },
  // 可选的播放速率，可以设置成自定义的数组
  playbackSpeed: {
    type: Array,
    default: () => [0.5, 0.75, 1, 1.25, 1.5, 2]
  },
  // 在左上角展示一个 logo，你可以通过 CSS 调整它的大小和位置
  // logo: {
  //   type: String,
  //   default: 'http://jiuaibuni.top/wp-content/uploads/2020/12/logo.png'
  // },
  // 视频信息
  video: {
    type: Object,
    default: {
      url: '', //视频地址
      type: 'mp4',
      customType: {}
    }
  },
  // 外挂字幕
  subtitle: {
    type: Object,
    default: {}
  },
  // 显示弹幕
  danmaku: {
    type: Object,
    default: {}
  },
  // 自定义右键菜单
  contextmenu: {
    type: Array,
    default: []
  },
  // 自定义进度条提示点
  highlight: {
    type: Array,
    default: []
  },
  // 阻止多个播放器同时播放，当前播放器播放时暂停其他播放器
  mutex: {
    type: Boolean,
    default: true
  }
})
onMounted(() => {
  let player = {
    container: videoRef.value,
    autoplay: props.autoplay,
    theme: props.theme,
    loop: props.loop,
    lang: props.lang,
    screenshot: props.screenshot,
    hotkey: props.hotkey,
    preload: props.preload,
    volume: props.volume,
    playbackSpeed: props.playbackSpeed,
    logo: props.logo,
    video: props.video,
    contextmenu: props.contextmenu,
    highlight: props.highlight,
    mutex: props.mutex
  }
  if (props.subtitle.url) {
    player.subtitle = props.subtitle
  }
  if (props.danmaku) {
    player.danmaku = props.danmaku
  }

  state.value.instance = new DPlayer(player)
  console.log(state.value)
})
// 销毁
onBeforeUnmount(() => {
  state.value.instance.destroy()
})
</script>

<style scoped>
:deep(.dplayer-danmaku .dplayer-danmaku-right.dplayer-danmaku-move) {
  /* transition: all 10s; */
  /* transform: translateX(100px) !important; */
  animation: move 120s ease-in-out;
}
@keyframes move {
  from {
    transform: translateX(100%);
  }
  to {
    transform: translateX(-1960px);
  }
}
</style>
