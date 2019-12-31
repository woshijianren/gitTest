package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cj
 * @date 2019/12/26
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }


    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }


}
