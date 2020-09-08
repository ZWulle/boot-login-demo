package com.zwulle.controller;

import com.zwulle.entites.User;
import com.zwulle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

    /**
     * 登录
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String email, String password) {
        //UserService验证用户账号密码是否正确
        boolean isTrue = userService.findByEmail(email, password);
        if (!isTrue) {
            // 验证未通过,错误页面
            return "redirect:error404.html";
        } else {
            // 验证通过,欢迎页面
            return "redirect:welcome.html";
        }

    }

    /**
     * 注册
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(User user) throws Exception {
//        System.out.println(user);
        boolean isSuccess = userService.addUser(user);
        if (isSuccess)
            //成功，返回到登陆页面
            return "redirect:index.html";
        else
            //失败，重新注册
            return "redirect:register.html";
    }
}


