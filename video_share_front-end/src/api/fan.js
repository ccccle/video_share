import request from '@/utils/request'
//关注
export const fanFollowService = (fan) => {
  return request.post('/api/v1/fan/follow', fan)
}
//获取用户的关注信息
export const fanGetFollowService = (userId, type, page, size) => {
  if (page && size) {
    return request.get('/api/v1/fan/getFollow', {
      params: {
        page,
        size,
        user_id: userId,
        type
      }
    })
  } else {
    return request.get('/api/v1/fan/getFollow', {
      params: {
        user_id: userId,
        type
      }
    })
  }
}
//获取用户的关注数量或粉丝数量
export const fanGetCountService = (userId, type) => {
  return request.get('/api/v1/fan/getCount', {
    params: {
      user_id: userId,
      type
    }
  })
}
