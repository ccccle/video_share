import request from '@/utils/request'
//获取视频所有弹幕
export const barrageGetList = (page, size, id) => {
  return request.get('/api/v1/barrage/list', {
    params: {
      page,
      size,
      id
    }
  })
}
//删除弹幕
export const barrageDel = (id) => {
  return request.delete('/api/v1/barrage/del', {
    params: {
      id
    }
  })
}
