package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cj
 * @date 2019/12/26
 */
@Controller
public class HomeController {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired(required = false)
    private AuthenticationManager authenticationManager;
    @RequestMapping("/")
    public String index(){
        return "index";
    }


    /**
     * 验证成功之后的信息,spring security用SecurityContext来封装
     * SecurityContext里面保存的主要是Authentication(验证令牌(token)),
     *  Authentication包含用户名,密码,授权(Authorities)
     *
     *  SecurityContextHolder用来保存SecurityContext
     * @return
     */
    @RequestMapping("/admin")
    public String admin(Authentication auth){


      String username =   ((User)SecurityContextHolder
              .getContext()
              .getAuthentication()
              .getPrincipal()).getUsername();
        return "admin";
    }
}
