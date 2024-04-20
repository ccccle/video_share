import request from '@/utils/request'
//通过token获取自己钱包
export const walletGetService = () => {
    return request.get('/api/v1/wallet/getSelf')
}
//通过 token开通钱包 
export const createWalletService = () => {
   return request.post('/api/v1/wallet/createWallet')
}