import request from '@/utils/request'
//获取分区列表
export const channelGetListService = () => {
  return request.get('/api/v1/channel/getChannelList')
}
//删除分区
export const channelDelService = (id) => {
  return request.delete('/api/v1/channel/del', {
    params: {
      id
    }
  })
}
export const channelAddService = (channelName) => {
  return request.get('/api/v1/channel/add', {
    params: {
      channelName
    }
  })
}
