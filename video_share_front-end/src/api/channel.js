import request from '@/utils/request'
//获取分区列表
export const channelGetListService = () => {
  return request.get('/api/v1/channel/getChannelList')
}
