package com.cle.video_share_backend.controller.v1;

import com.cle.video_share_backend.common.ResponseResult;
import com.cle.video_share_backend.pojo.Barrage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/barrage")
public class BarrageController {
    @GetMapping("/get/v3/")
    public ResponseResult get(String id){
        List<Object> list = new ArrayList<>();
        Barrage barrage = new Barrage();
        barrage.setTime(1.188);
        barrage.setText("好家伙，我直接好家伙");
        barrage.setAuthor("小明");
        barrage.setType("0");
        barrage.setColor("16777215");

        List<Object> list2 = new ArrayList<>();
        list2.add(barrage.getTime());
        list2.add(barrage.getType());
        list2.add(barrage.getColor());
        list2.add(barrage.getAuthor());
        list2.add(barrage.getText());
        list.add(list2);
        return ResponseResult.success(0,"成功",list);

    }
}
