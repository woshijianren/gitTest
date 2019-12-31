package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cj
 * @date 2019/12/31
 */
@Controller
public class MultiChainController {


    @RequestMapping("/bar/admin")
    public String barAdmin(){
        return "admin";
    }

    @RequestMapping("/foo/admin")
    public String fooAdmin(){
        return "admin";
    }
}
