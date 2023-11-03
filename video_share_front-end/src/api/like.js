import request from '@/utils/request'
//点赞
export const likeLikeService = (like) => {
  return request.post('/api/v1/like/like', like)
}
