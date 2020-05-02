package com.mgyh.supermaket.util;

import com.alibaba.fastjson.JSONObject;

public class Result {
//            map1.put("code",20000);
//        map1.put("data",object);

    private int code;
    private Object data;

    public Result(){
        setCode(20000);
        setData(new JSONObject());
    }

    public Result(Object object){
        setCode(20000);
        setData(object);
    }

    public Result(int code){
        setCode(code);
        setData(new JSONObject());
    }

    public Result(int code,Object object){
        setCode(code);
        setData(object);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object object) {
        this.data = object;
    }
}
