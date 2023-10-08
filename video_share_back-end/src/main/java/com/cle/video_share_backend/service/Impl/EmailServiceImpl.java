package com.cle.video_share_backend.service.Impl;

import com.apifan.common.random.source.NumberSource;
import com.cle.video_share_backend.service.EmailService;
import com.cle.video_share_backend.utils.EmailSpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private EmailSpringUtil emailSpringUtil;
    @Override
    public int sendEmailCode(String email) {
        //生成1个1000~10000(不含)之间的随机整数
        int code = NumberSource.getInstance().randomInt(1000, 10000);
        try {
            emailSpringUtil.sendEmail("短视频分享验证码",String.valueOf(code),false,"验证码",email,null,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送失败");
        }
        return code;
    }
}
