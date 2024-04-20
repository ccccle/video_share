import request from '@/utils/request'

//分页获取奖励
export const rewardPageService = (page,size) => {
    return request.get("/api/v1/reward/page", {
        params: {
            page,size
        }
    })
}
//添加活动
export const rewardAddService = (reward) => {
    console.log(1111);
    return request.post("/api/v1/reward/add",reward)
}