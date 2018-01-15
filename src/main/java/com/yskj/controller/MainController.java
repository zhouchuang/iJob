package com.yskj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 主要的控制层
 *
 * @author zhouchuang
 * @create 2018-01-15 15:45
 */
@Controller
@RequestMapping("")
public class MainController {
    /**
     * 登陆页面
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }
}
