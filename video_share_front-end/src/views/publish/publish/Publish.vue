<script setup>
import { Picture, VideoPlay } from '@element-plus/icons-vue'
import { channelGetListService } from '@/api/channel.js'
import { videoUploadService } from '@/api/video.js'
import { ref } from 'vue'
const loading = ref(false)

//视频
const imgUrl = ref('')
const videoUrl = ref('')
const video = ref({
  video_name: '',
  video_description: '',
  video_data: '',
  video_cover: '',
  channel_id: '',
  channel_name: ''
})
//上传视频
const uploadVideo = async () => {
  await videoFormRef.value.validate()
  await validateVideoData()
  loading.value = true
  await videoUploadService(video.value)
  loading.value = false
  ElMessage.success('上传成功')
  video.value = {}
  imgUrl.value = ''
  videoUrl.value = ''
}
//分区
const channel_list = ref([])
const getChannelList = async () => {
  const res = await channelGetListService()
  channel_list.value = res.data.data
}
getChannelList()
//图片文件转换成url访问
const onImageFile = (uploadFile) => {
  imgUrl.value = URL.createObjectURL(uploadFile.raw)
  video.value.video_cover = uploadFile.raw
}
//视频转换成url访问
const onVideoFile = (uploadFile) => {
  videoUrl.value = URL.createObjectURL(uploadFile.raw)
  video.value.video_data = uploadFile.raw
  console.log(video.value.video_data)
}
//视频规则验证
const videoFormRef = ref()
const videoRules = {
  video_name: [
    { required: true, message: '必须填写视频标题', trigger: 'blur' }
  ],
  video_description: [
    { required: true, message: '必须填写视频描述', trigger: 'blur' }
  ],
  channel_id: [
    { required: true, message: '必须选择视频分区', trigger: 'change' }
  ]
}
//检测视频是否为空
const validateVideoData = () => {
  return new Promise((resolve, reject) => {
    if (video.value.video_data) {
      resolve()
    } else {
      ElMessage.error('必须上传视频')
      reject()
    }
  })
}
</script>
<template>
  <div class="upload-container">
    <div class="header"><span class="title">发布视频</span></div>
    <el-form
      :rules="videoRules"
      ref="videoFormRef"
      v-loading="loading"
      element-loading-text="上传中..."
      :model="video"
    >
      <div class="upload-item">
        <el-form-item>
          <el-upload
            class="upload-image"
            drag
            :show-file-list="false"
            :auto-upload="false"
            :on-change="onImageFile"
          >
            <div v-if="!imgUrl">
              <el-icon class="el-icon--upload"><Picture /></el-icon>
              <div class="el-upload__text">
                拖拽到此处或者 <em>点击上传</em>
              </div>
            </div>
            <el-image v-else :src="imgUrl"> </el-image>

            <template #tip>
              <div class="el-upload__tip">上传封面;默认使用第一帧作为封面</div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-upload class="upload-video" drag :on-change="onVideoFile">
            <div v-if="!videoUrl">
              <el-icon class="el-icon--upload"><VideoPlay /></el-icon>
              <div class="el-upload__text">
                拖拽到此处或者 <em>点击上传</em>
              </div>
            </div>
            <video
              v-else
              :src="videoUrl"
              controls="true"
              style="height: 100%; width: 100%"
            ></video>
            <template #tip>
              <div class="el-upload__tip">上传视频;最大200MB</div>
            </template>
          </el-upload>
        </el-form-item>
      </div>
      <el-form-item prop="video_name">
        <el-input
          v-model="video.video_name"
          placeholder="填写标题，可能获得更多赞哦~"
        ></el-input>
      </el-form-item>
      <el-form-item prop="video_description">
        <el-input
          v-model="video.video_description"
          type="textarea"
          :autosize="{ minRows: 4 }"
          placeholder="填写更全面的描述信息，让更多人看到你吧！"
        ></el-input>
      </el-form-item>
      <el-form-item prop="channel_id">
        <el-select v-model="video.channel_id" filterable placeholder="选择分区">
          <el-option
            v-for="item in channel_list"
            :key="item.id"
            :label="item.channel_name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          @click="uploadVideo"
          class="button-publish"
          type="primary"
          size="large"
          >发布视频</el-button
        >
        <el-button size="large">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<style scoped>
.upload-container {
  .upload-item {
    display: flex;
    justify-content: space-around;
  }
  .upload-video {
    margin-top: 10px;
    width: 200px;
  }
  .upload-image {
    margin-top: 10px;
    width: 200px;
  }
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
}
</style>
