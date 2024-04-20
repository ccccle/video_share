import request from '@/utils/request'
//分页获取当前登录用户的奖励记录
export const rewardHistoryPageService = (page,size) => {
    return request.get('/api/v1/rewardHistory/page', {
        params: {
            page,size
        }
    } )
  }