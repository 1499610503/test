package com.example.ruijisboot.common;

import lombok.Data;

@Data
public class R<T> {
    private Integer code;
    private T data;
    private String msg;

    public R(){}

    private R(int code, T data){
        this.code = code;
        this.data = data;
    }
    private R(int code, T data, String msg){
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    private R(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static R success(Object data){
        return  new R(0, data);
    };
    public static R success(Object data, String msg){
        return  new R(0, data,msg);
    };

    public static R error(String msg){
        return  new R(1, msg);
    };
}
