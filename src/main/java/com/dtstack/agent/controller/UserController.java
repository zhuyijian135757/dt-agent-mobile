package com.dtstack.agent.controller;

import com.dtstack.agent.lang.Api;
import com.dtstack.agent.service.UserService;
import com.dtstack.agent.vo.TenantVo;
import com.dtstack.agent.vo.UrlVo;
import com.dtstack.agent.vo.UserVo;
import com.dtstack.plat.lang.base.JSONs;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @program: dt-mobile-agent
 * @description: 用户rest接口
 * @author: terry.zhu
 * @create: 2019-04-02 11:23
 **/
@Slf4j
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
    public R<List<TenantVo>> getAllUser(){
        return new APITemplate<List<TenantVo>>(){

            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<TenantVo> process() throws BizException {
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
    public R<UrlVo> logout(@RequestParam("platName") String platName,
                           @RequestParam("cookieValue") String cookieValue){
        return new APITemplate<UrlVo>(){

            @Override
            protected void checkParams() throws IllegalArgumentException {
                Assert.notNull(platName,"平台名称不能为空");
                Assert.notNull(cookieValue,"cookie值不能为空");
            }

            @Override
            protected UrlVo process() throws BizException {
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
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R<Object> login(@RequestParam(value = "verify_code",required = false) String verifyCode,
                           @RequestParam(value="random_seq",required = false) String randomSeq,
                           @RequestParam(value="tenant_id",required = false) String tenantId,
                           @RequestParam("platName") String platName,
                           @RequestParam("targetUri") String platUrl,
                           HttpServletRequest  request,
                           HttpServletResponse response){
        return new APITemplate<Object>(){

            @Override
            protected void checkParams() throws IllegalArgumentException {
                Assert.notNull(platName,"平台名称不能为空");
                //Assert.notNull(verifyCode,"verifyCode不能为空");
                Assert.notNull(platUrl,"targetUri不能为空");
                log.info("verify_code:{}",verifyCode);
                log.info("random_seq:{}",randomSeq);
                log.info("tenantId:{}",tenantId);
                log.info("platName:{}",platName);
                log.info("platUrl:{}",platUrl);
            }

            @Override
            protected Boolean process() throws BizException {
                userService.login(verifyCode,randomSeq,tenantId,platName,platUrl,request,response);
                return null;
            }
        }.execute();
    }




}
