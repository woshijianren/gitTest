package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.ResponseVO;

/**
 * @author cj
 * @date 2019/12/26
 */
@RestController
public class RestDemoController {



    @RequestMapping("/api/query")
    public ResponseVO admin(){

        ResponseVO vo = new ResponseVO("200", "admin", "guanlidata");

        return vo;
    }


}
