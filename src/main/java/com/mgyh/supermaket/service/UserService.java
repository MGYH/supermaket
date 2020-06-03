package com.mgyh.supermaket.service;

import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.entity.User;
import com.mgyh.supermaket.repository.SearchRepository;
import com.mgyh.supermaket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author MGYH
 * @date 2020/4/7
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SearchRepository searchRepository;

    public JSONObject verification(User user){
        List<User> userList = userRepository.findByUsername(user.getUsername());
        JSONObject object = new JSONObject();
        if(!userList.isEmpty()){
            if(userList.get(0).getPassword().equals(user.getPassword())){
                JSONObject token = new JSONObject();
                object.put("code",20000);
                token.put("token",userList.get(0).getId());
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

    public User verification(int id){
        return userRepository.findById(id).get();
    }

    public JSONObject findAll(JSONObject object, int pageNo, int pageSize) {
        String sql = "select u " +
                "from User u where 1=1 ";
        if(object.containsKey("username")){
            sql+=" and username = :username";
        }
        if(object.containsKey("name")){
            sql+=" and name like :name";
            object.put("name","%"+object.getString("name")+"%");
        }
        if(object.containsKey("role")){
            sql+=" and role = :role";
        }
        return searchRepository.commonSearch(sql,object,pageNo,pageSize);
    }

    @Transactional
    public User initPassword(User user){
        return userRepository.save(user);
    }
}
