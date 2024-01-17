package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.mapper.WalletMapper;
import com.cle.video_share_backend.pojo.Wallet;
import com.cle.video_share_backend.service.WalletService;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements WalletService {
    @Override
    public Wallet getByUserId(Long userId) {
        LambdaQueryWrapper<Wallet> walletLambdaQueryWrapper =  new LambdaQueryWrapper<>();
        walletLambdaQueryWrapper.eq(Wallet::getUserId,userId);
        Wallet wallet = this.getOne(walletLambdaQueryWrapper);
        return wallet;
    }
}
