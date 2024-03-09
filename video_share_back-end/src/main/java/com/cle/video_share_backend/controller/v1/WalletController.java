package com.cle.video_share_backend.controller.v1;

import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.exception.ServiceException;
import com.cle.video_share_backend.pojo.User;
import com.cle.video_share_backend.pojo.Wallet;
import com.cle.video_share_backend.service.WalletService;
import com.cle.video_share_backend.utils.JWTUtils;
import com.cle.video_share_backend.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping("/getSelf")
    public ResponseResult getSelf() throws ServiceException {
        User user = UserThreadLocal.get();
        Wallet wallet = walletService.getWallet(user.getId());
        return ResponseResult.success(wallet);
    }
    @PostMapping("/createWallet")
    public ResponseResult createWallet() throws ServiceException {
        walletService.createWallet();
        return ResponseResult.success();
    }
}
