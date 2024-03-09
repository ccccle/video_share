package com.cle.video_share_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cle.video_share_backend.exception.ServiceException;
import com.cle.video_share_backend.pojo.Wallet;

public interface WalletService extends IService<Wallet> {
    /**
     * 根据userId查询
     * @param userId
     * @return
     */
    public Wallet getByUserId(Long userId);


    Wallet getWallet(Long userId) throws ServiceException;

    void createWallet() throws ServiceException;

}
