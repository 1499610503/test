package com.example.ruijisboot.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@ApiModel("菜品选项实体类")
@Data
@TableName("dishe_option")
public class DisheOption implements Serializable {

    @ApiModelProperty("选项Id")
    @TableField(value = "id", select = false)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("选项标题")
    private String title;

    @ApiModelProperty("菜品id")
    @TableField(value = "dishe_id")
    private Long disheId;

    @ApiModelProperty("选项值")
    @TableField(value = "value_string")
    private String valueString;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime updateTime;
}
