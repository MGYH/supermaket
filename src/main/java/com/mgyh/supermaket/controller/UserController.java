package com.mgyh.supermaket.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.mgyh.supermaket.entity.Goods;
import com.mgyh.supermaket.entity.User;
import com.mgyh.supermaket.service.GoodsService;
import com.mgyh.supermaket.service.UserService;
import com.mgyh.supermaket.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Todo Token机制先不做
 * @author MGYH
 * @date 2020/4/7
 */
@RestController
@CrossOrigin
public class UserController {
    @Autowired
    public UserService userService;

    @PostMapping("/user/login")
    public Object loginin(@RequestBody User user){
        JSONObject object = userService.verification(user);
        return new Result(object.getIntValue("code"),object.getString("message")
                ,object.getJSONObject("token"));
    }
    @GetMapping("/user/info")
    public Object getUserInfo(){
        JSONObject object = new JSONObject();
        JSONObject user = new JSONObject();
        user.put("name","admin");
        user.put("id","1");
        object.put("name", user);
        return new Result(object);
    }
    @PostMapping("/user/logout")
    public Object loginOut(){
        return new Result();
    }
}
