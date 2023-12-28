import request from '@/utils/request'
//发送评论
export const commentSendService = (comment) => {
  return request.post('/api/v1/comment/sendComment', comment)
}
//获取所有评论
export const commentGetService = (videoId, page, size) => {
  return request.get('/api/v1/comment/getComment', {
    params: {
      page,
      size,
      video_id: videoId
    }
  })
}
//删除评论
export const commentDel = (id) => {
  return request.delete('/api/v1/comment/delComment', {
    params: {
      id
    }
  })
}
