package com.cle.video_share_backend.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.pojo.RewardHistory;
import com.cle.video_share_backend.service.RewardHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rewardHistory")
public class RewardHistoryController {
    @Autowired
    private RewardHistoryService rewardHistoryService;

    @GetMapping("/page")
    public ResponseResult page(Integer page,Integer size){
        IPage rewardHistoryIPage = rewardHistoryService.pageSelf(page, size);
        return ResponseResult.success(rewardHistoryIPage);
    }
}
