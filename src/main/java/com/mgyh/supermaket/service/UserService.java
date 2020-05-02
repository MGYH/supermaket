package com.mgyh.supermaket.service;

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

    public User verification(User user){
        List<User> userList = userRepository.findByUsername(user.getUsername());
        if(userList.size() > 0){
            return userList.get(0);
        }
        return null;
    }
}
