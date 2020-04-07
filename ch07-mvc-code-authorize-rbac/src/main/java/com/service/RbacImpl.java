package com.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cj
 * @date 2020/1/4
 */
@Component("rbac")
public class RbacImpl {

    public boolean xxx(HttpServletRequest req, Authentication authentication) {
        System.out.println("-----debug: request = " + req);
        System.out.println("-----debug: authentication = " + authentication);
       String username = authentication.getName();
        List<String> urls = new ArrayList<>();
       //下面的代码模拟从数据库中找到此用户的所有可以访问的url
        if("user".equalsIgnoreCase(username)){
            urls.add("/user");
            urls.add("/user2");
        }else{
            urls.add("/admin");
            urls.add("/admin2");
        }
        //这里是当前请求的url
        String currrentRequestUrl = req.getServletPath();

        return urls.contains(currrentRequestUrl);
    }
}
