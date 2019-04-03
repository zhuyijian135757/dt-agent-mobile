package com.dtstack.agent.controller;

import com.dtstack.agent.lang.Api;
import com.dtstack.agent.service.UserService;
import com.dtstack.agent.vo.UserVo;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: dt-mobile-agent
 * @description: 用户rest接口
 * @author: terry.zhu
 * @create: 2019-04-02 11:23
 **/

@RestController
@RequestMapping(Api.API_PREFIX + "/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public R<UserVo> getUser(@RequestParam("userId") String userId){
        return new APITemplate<UserVo>(){

            @Override
            protected void checkParams() throws IllegalArgumentException {
                Assert.notNull(userId,"userId不能为空");
            }

            @Override
            protected UserVo process() throws BizException {
                return userService.getUser(userId);
            }
        }.execute();
    }

    /**
     * 获取所有用户信息
     * @return
     */
    @RequestMapping(value = "/getAllUser", method = RequestMethod.GET)
    public R<Object> getAllUser(){
        return new APITemplate<Object>(){

            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Object process() throws BizException {
                return userService.getAllUser();
            }
        }.execute();
    }


    /**
     * 更新用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public R<String> updateUser(@RequestParam("userId") String userId){
        return new APITemplate<String>(){

            @Override
            protected void checkParams() throws IllegalArgumentException {
                Assert.notNull(userId,"userId不能为空");
            }

            @Override
            protected String process() throws BizException {
                return userService.updateUser(userId);
            }
        }.execute();
    }


    /**
     * 平台登出
     * @param platName
     * @param cookieValue
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public R<String> logout(@RequestParam("platName") String platName,
                            @RequestParam("cookieValue") String cookieValue){
        return new APITemplate<String>(){

            @Override
            protected void checkParams() throws IllegalArgumentException {
                Assert.notNull(platName,"平台名称不能为空");
                Assert.notNull(cookieValue,"cookie值不能为空");
            }

            @Override
            protected String process() throws BizException {
                return userService.logout(platName,cookieValue);
            }
        }.execute();
    }


    /**
     * 认证系统回调登陆接口
     * @param verifyCode
     * @param platName
     * @param platUrl
     * @param response
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public R<Object> login(@RequestParam("verifyCode") String verifyCode,
                           @RequestParam("platName") String platName,
                           @RequestParam("targetUri") String platUrl,
                           HttpServletRequest  request,
                           HttpServletResponse response){
        return new APITemplate<Object>(){

            @Override
            protected void checkParams() throws IllegalArgumentException {
                Assert.notNull(platName,"平台名称不能为空");
                Assert.notNull(verifyCode,"verifyCode不能为空");
                Assert.notNull(platUrl,"targetUri不能为空");
            }

            @Override
            protected Boolean process() throws BizException {
                userService.login(verifyCode,platName,platUrl,request,response);
                return null;
            }
        }.execute();
    }




}
