package com.cle.video_share_backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cle.video_share_backend.exception.ServiceException;
import com.cle.video_share_backend.mapper.WalletMapper;
import com.cle.video_share_backend.pojo.User;
import com.cle.video_share_backend.pojo.Wallet;
import com.cle.video_share_backend.service.WalletService;
import com.cle.video_share_backend.utils.UserThreadLocal;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements WalletService {
    @Override
    public Wallet getByUserId(Long userId) {
        LambdaQueryWrapper<Wallet> walletLambdaQueryWrapper =  new LambdaQueryWrapper<>();
        walletLambdaQueryWrapper.eq(Wallet::getUserId,userId);
        Wallet wallet = this.getOne(walletLambdaQueryWrapper);
        return wallet;
    }

    @Override
    public Wallet getWallet(Long userId) throws ServiceException {
        Wallet wallet = this.getByUserId(userId);
        if(Objects.isNull(wallet)){
            throw new ServiceException("未开通创作奖励");
        }
        return wallet;

    }

    @Override
    public void createWallet() throws ServiceException {
        User user = UserThreadLocal.get();
        Wallet wallet = new Wallet();
        wallet.setBalance(0L);
        wallet.setUserId(user.getId());
        LambdaQueryWrapper<Wallet> eq = new LambdaQueryWrapper<Wallet>().eq(Wallet::getUserId, user.getId());
        Wallet one = this.getOne(eq);
        if(one!=null){
            throw new ServiceException("已经开通过了");
        }
        this.save(wallet);
    }
}
