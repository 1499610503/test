package com.example.ruijisboot.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


import com.baomidou.mybatisplus.annotation.*;
        import com.fasterxml.jackson.annotation.JsonFormat;
        import io.swagger.annotations.ApiModel;
        import io.swagger.annotations.ApiModelProperty;
        import lombok.Data;

        import java.io.Serializable;
        import java.time.LocalDateTime;

@ApiModel("菜品实体类")
@Data
@TableName("dishe")
public class Dishe implements Serializable {

    @ApiModelProperty("菜品Id")
    @TableField(value = "id", select = false)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("菜品描述")
    private String description;

    @ApiModelProperty("状态")// 0正常 1停用
    private Integer status;

    @ApiModelProperty("图片")
    private String img;

    @ApiModelProperty("分类id")
    @TableField(value = "category_id")
    private String categoryId;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime updateTime;
}