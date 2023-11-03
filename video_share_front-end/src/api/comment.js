import request from '@/utils/request'
//发送评论
export const commentSendService = (comment) => {
  return request.post('/api/v1/comment/sendComment', comment)
}
//获取所有评论
export const commentGetService = (videoId) => {
  return request.get('/api/v1/comment/getComment', {
    params: {
      video_id: videoId
    }
  })
}
