package com.cle.video_share_backend.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.pojo.Barrage;
import com.cle.video_share_backend.service.BarrageService;
import com.cle.video_share_backend.vo.BarrageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/barrage")
public class BarrageController {
    @Autowired
    private BarrageService barrageService;
    @GetMapping("/v3/")
    public ResponseResult get(Long id){
        List<List<Object>> barrage = barrageService.getBarrage(id);
        return ResponseResult.success(0,"成功",barrage);
    }
    @PostMapping("/v3/")
    public ResponseResult get(@RequestBody BarrageVo barrageVo){
        barrageService.save(barrageVo);
        return ResponseResult.success();
    }
    @GetMapping("/list")
    public ResponseResult list(Integer page,Integer size,Long id){
        IPage<BarrageVo> barrage = barrageService.getBarrageList(page,size,id);
        return ResponseResult.success(0,"成功",barrage);
    }
    @DeleteMapping("/del")
    public ResponseResult del(Long id){
        barrageService.removeById(id);
        return ResponseResult.success();
    }
}
