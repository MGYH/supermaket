package com.mgyh.supermaket.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.entity.Goods;
import com.mgyh.supermaket.entity.User;
import com.mgyh.supermaket.service.GoodsService;
import com.mgyh.supermaket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MGYH
 * @date 2020/4/7
 */
//Todo Token机制先不做
@RestController
@CrossOrigin
public class UserController {
    @Autowired
    public UserService userService;

    @PostMapping("/user/login")
    public Object loginin(){
        Map<String, Object> map;
        JSONObject object;
        map = new HashMap<>();
        Map<String, Object> map1;
        JSONObject object1;
        map1 = new HashMap<>();
        map1.put("token","this_is_a_token");
        map1.put("msg","this_is_a_token");
        map.put("code",20000);
        object1 = new JSONObject(map1);
        map.put("msg","this_is_a_token");
        map.put("data",object1);
        object = new JSONObject(map);
        System.out.println("this is a  token");
        return object;
    }
    @GetMapping("/user/info")
    public Object getUserInfo(){
        Map<String, Object> map;
        JSONObject object;
        map = new HashMap<>();
        JSONObject object1;
        Map<String, Object> map2 = new HashMap<>();
        map2.put("roles","测试");
        object1 = new JSONObject(map2);
        JSONArray array = new JSONArray();
        array.add(object1);
        map.put("name","this_is_a_token");
        map.put("avatar","this_is_a_token");
        map.put("roles",object1);
        object = new JSONObject(map);
        Map<String, Object> map1;
        JSONObject result;
        map1 = new HashMap<>();
        map1.put("code",20000);
        map1.put("data",object);
        result = new JSONObject(map1);
        return result;
    }
    @PostMapping("/user/logout")
    public User loginOut(@RequestBody User user){
        System.out.println(user.toString());
        return userService.verification(user);
    }
}
