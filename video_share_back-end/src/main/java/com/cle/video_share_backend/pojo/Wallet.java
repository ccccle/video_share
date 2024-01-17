package com.cle.video_share_backend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
@TableName("wallet")
public class Wallet {
    @TableId
    private Long id;
    @TableField("balance")
    private Long balance;
    @TableField("user_id")
    private Long userId;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(value = "flag", fill = FieldFill.INSERT)
    @TableLogic
    private Integer flag;
}
