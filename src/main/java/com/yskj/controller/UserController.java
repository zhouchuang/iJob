package com.yskj.controller;

import com.yskj.domain.ResponseObj;
import com.yskj.domain.User;
import com.yskj.service.serviceImpl.UserServiceImpl;
import com.yskj.utils.GsonUtils;
import com.yskj.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户控制层
 *
 * @author zhouchuang
 * @create 2018-01-15 17:35
 */

@Controller
@RequestMapping("/userAction")
public class UserController {
    @Autowired
    private UserServiceImpl userService;    //自动载入Service对象
    private ResponseObj responseObj;
    /**
     * 为什么返回值是一个ModelAndView，ModelAndView代表一个web页面<br/>
     * setViewName是设置一个jsp页面的名称
     *
     * @param req  http请求
     * @param user 发起请求后，spring接收到请求，然后封装的bean数据
     * @return 返回一个web页面
     * @throws Exception
     */
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ModelAndView reg(HttpServletRequest req, User user) throws Exception {
        ModelAndView mav = new ModelAndView();  //创建一个jsp页面对象
        mav.setViewName("home");    //设置JSP文件名
        if (null == user) {
            mav.addObject("message", "用户信息不能为空！");  //加入提示信息
            return mav; //返回页面
        }
        if (StringUtils.isEmpty(user.getLoginId()) || StringUtils.isEmpty(user.getPwd())) {
            mav.addObject("message", "用户名或密码不能为空！");
            return mav;
        }
        if (null != userService.findUser(user)) {
            mav.addObject("message", "用户已经存在！");
            return mav;
        }
        try {
            userService.add(user);
        } catch (Exception e) {
            e.printStackTrace();
            mav.addObject("message", "错误：用户其他信息错误");
            return mav;
        }
        mav.addObject("code", 110);
        mav.addObject("message", "恭喜。注册成功");
        req.getSession().setAttribute("user", user);
        return mav;
    }

    /**
     * 登录接口
     *
     * @param req
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {
            "application/json; charset=utf-8"})
    @ResponseBody
    public ModelAndView login(HttpServletRequest req, User user) {
        ModelAndView mav = new ModelAndView("home");
        String result;
        if (null == user) {
            responseObj = new ResponseObj<User>();
            responseObj.setCode(ResponseObj.EMPUTY);
            responseObj.setMsg("登录信息不能为空");
            result = GsonUtils.gson.toJson(responseObj);
            mav.addObject("result", result);
            return mav; //返回页面
        }
        if (StringUtils.isEmpty(user.getLoginId()) || StringUtils.isEmpty(user.getPwd())) {
            responseObj = new ResponseObj<User>();
            responseObj.setCode(ResponseObj.FAILED);
            responseObj.setMsg("用户名或密码不能为空");
            result = GsonUtils.gson.toJson(responseObj);
            mav.addObject("result", result);
            return mav;
        }
        //查找用户
        User user1 = userService.findUser(user);
        if (null == user1) {
            responseObj = new ResponseObj<User>();
            responseObj.setCode(ResponseObj.EMPUTY);
            responseObj.setMsg("未找到该用户");
            result = GsonUtils.gson.toJson(responseObj);
        } else {
            if (user.getPwd().equals(user1.getPwd())) {
                responseObj = new ResponseObj<User>();
                responseObj.setCode(ResponseObj.OK);
                responseObj.setMsg(ResponseObj.OK_STR);
                result = GsonUtils.gson.toJson(responseObj);
            } else {
                responseObj = new ResponseObj<User>();
                responseObj.setCode(ResponseObj.FAILED);
                responseObj.setMsg("用户密码错误");
                result = GsonUtils.gson.toJson(responseObj);
            }
        }
        mav.addObject("result", result);
        return mav;
    }

}
