package com.example.ruijisboot.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel("用户实体类")
@Data
@TableName("employee")
public class Employee implements Serializable {

    @ApiModelProperty("用户Id")
    @TableField(value = "id", select = false)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("用户名称")
    private String name;

    @ApiModelProperty("用户密码")
    private String password;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime updateTime;
}
