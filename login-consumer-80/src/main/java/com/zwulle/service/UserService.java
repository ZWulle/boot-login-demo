package com.zwulle.service;

import com.zwulle.dao.UserDao;
import com.zwulle.entites.User;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MsgConsumer msgConsumer;
    @Autowired
    private MsgProduce msgProduce;

    @Override
    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public User save(User user) {
        return mongoTemplate.save(user);
    }

    @Override
    public User insert(User user) {
        return mongoTemplate.insert(user);
    }

    @Override
    public boolean findByEmail(String email, String psw) {
        String collection = "user";
        //验证用户输入密码是否正确
        List<User> userList = mongoTemplate.find(
                new Query(Criteria.where("email_addr").is(email)),
                User.class, collection);
        //数据库中不能出现相同邮箱
        if (userList.size() == 1) {
            for (User user : userList) {
                if (user.getEmail().equals(email) && user.getPassword().equals(psw)) {
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    @Override
    public boolean findUser(String email) {
        String collection = "user";
        //查询邮箱用户
        List<User> userList = mongoTemplate.find(
                new Query(Criteria.where("email_addr").is(email)), User.class, collection);
        if (userList.isEmpty()) {
            return true;
        }
        return false;
    }

    //用户注册
    @Override
    public boolean addUser(User user) throws Exception {
        String collection = "user";
        //1、先查询当前邮箱是否已被注册
        boolean hasUser = findUser(user.getEmail());
//        System.out.println(hasUser);
        //2、写入数据库中
        if (hasUser) {
            User reUser = mongoTemplate.save(user, collection);
            if (reUser != null) {
                //插入成功
                //3、将用户信息写到RabbitMQ中
                msgProduce.msgProduce(user);
//               System.out.println("ok");
                return true;
            }
        }
        //已被注册,插入失败
        return false;
    }
}