package com.zwulle.dao;

import com.zwulle.entites.User;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserDao {
    //    List<User> findAll();
    User save(User user);

    User insert(User user);

    //登陆查找
    boolean findByEmail(@Value("email") String email, String password);

    //注册判断(当前用户是否已经注册)
    boolean findUser(@Value("email") String email);

    //添加用户
    boolean addUser(User user) throws Exception;

    List<User> findAll();
}
