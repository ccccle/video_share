import request from '@/utils/request'
//上传视频
export const videoUploadService = (video) => {
  var formData = new FormData()
  for (let key in video) {
    formData.append(key, video[key])
  }
  return request.post('/api/v1/video/uploadVideo', formData, {
    headers: {
      'Content-Type': 'multipart/form-data' // 设置请求头为 multipart/form-data
    }
  })
}
//获取视频流
export const videoFeedService = (size, page, searchKey, channelId) => {
  return request.get('/api/v1/video/feed', {
    params: {
      size,
      page,
      key: searchKey,
      channel_id: channelId
    }
  })
}
//获取具体视频
export const videoGetService = (id) => {
  return request.get('/api/v1/video/getVideo', {
    params: {
      id
    }
  })
}
//根据用户来获取视频
export const videoUserService = (page, size, userId) => {
  return request.get('/api/v1/video/getUserVideo', {
    params: {
      page,
      size,
      user_id: userId
    }
  })
}
//删除视频
export const videoDelSerivce = (id) => {
  return request.delete('/api/v1/video/delVideo', {
    params: {
      id
    }
  })
}
