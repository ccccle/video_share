package com.cle.video_share_backend.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.pojo.Reward;
import com.cle.video_share_backend.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reward")
public class RewardController {
    @Autowired
    private RewardService rewardService;
    @GetMapping("/page")
    public ResponseResult page(Integer page,Integer size){
        IPage page1= rewardService.pageReward(page,size);
        return ResponseResult.success(page1);
    }
    @PostMapping("/add")
    public ResponseResult add(@RequestBody Reward reward){
        rewardService.save(reward);
        return ResponseResult.success();
    }
}
