package com.mgyh.supermaket.controller;

import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.entity.User;
import com.mgyh.supermaket.service.UserService;
import com.mgyh.supermaket.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    public Object getUserInfo(int token){
        User user= userService.verification(token);
        JSONObject object = new JSONObject();
        object.put("name",user);
        return new Result(object);
    }
    @PostMapping("/user/logout")
    public Object loginOut(){
        return new Result();
    }


    @PostMapping("/user/findAll")
    public Object findAll(@RequestBody JSONObject object,int pageNo,int pageSize){
        return new Result(userService.findAll(object,pageNo,pageSize));
    }

    @PostMapping("/user/initPassword")
    public Object initPassword(@RequestBody JSONObject object){
        System.out.println(object.toJSONString());
        User user = object.getObject("user", User.class);
        user.setPassword("123456");
        userService.initPassword(user);
        return new Result();
    }
}
