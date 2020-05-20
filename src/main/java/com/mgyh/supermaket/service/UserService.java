package com.mgyh.supermaket.service;

import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.entity.User;
import com.mgyh.supermaket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MGYH
 * @date 2020/4/7
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public JSONObject verification(User user){
        List<User> userList = userRepository.findByUsername(user.getUsername());
        JSONObject object = new JSONObject();
        if(!userList.isEmpty()){
            if(userList.get(0).getPassword().equals(user.getPassword())){
                JSONObject token = new JSONObject();
                object.put("code",20000);
                token.put("token","abcdefg");
                object.put("token",token);
            }else{
                object.put("code",20002);
                object.put("message","密码错误");
            }
        }else {
            object.put("code",20001);
            object.put("message","用户名不存在");
        }
        return object;
    }
}
