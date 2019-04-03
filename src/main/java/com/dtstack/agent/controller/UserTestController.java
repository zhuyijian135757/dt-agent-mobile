package com.dtstack.agent.controller;

import com.dtstack.agent.lang.Api;
import com.dtstack.agent.vo.UserVo;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: dt-mobile-agent
 * @description: 测试类，模拟流程用
 * @author: terry.zhu
 * @create: 2019-04-03 11:32
 **/

@RestController
@RequestMapping(Api.API_PREFIX + "/user-test")
public class UserTestController {

    /**
     * 测试用途
     * @return
     */
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public R<UserVo> auth(@RequestParam("verifyCode") String verifyCode,
                          @RequestParam("randomSeq") String randomSeq){
        return new APITemplate<UserVo>(){
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected UserVo process() throws BizException {
                UserVo userVo=new UserVo();
                userVo.setUserId("110");
                userVo.setUserName("rose");
                userVo.setOrgId("111");
                userVo.setOrgName("大数据中心");
                userVo.setHomeCounty("zj");
                userVo.setHomeCountyName("浙江省");
                userVo.setHomeCity("hz");
                userVo.setHomeCityName("杭州");
                userVo.setMailAddr("123213@133.com");
                userVo.setMobilePhone("13131313");
                userVo.setNickName("匿名");
                return userVo;
            }
        }.execute();
    }


    /**
     * 测试用途
     * @return
     */
    @RequestMapping(value = "/siguv", method = RequestMethod.GET)
    public R<UserVo> sigUserUrl(@RequestParam("userId") String userId){
        return new APITemplate<UserVo>(){
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected UserVo process() throws BizException {
                UserVo userVo=new UserVo();
                userVo.setUserId("1101");
                userVo.setUserName("rose");
                userVo.setOrgId("111");
                userVo.setOrgName("大数据中心1");
                userVo.setHomeCounty("zj");
                userVo.setHomeCountyName("浙江省");
                userVo.setHomeCity("hz");
                userVo.setHomeCityName("杭州");
                userVo.setMailAddr("123213@133.com");
                userVo.setMobilePhone("13131313");
                userVo.setNickName("匿名1");
                return userVo;
            }
        }.execute();
    }


    /**
     * 测试用途
     * @return
     */
    @RequestMapping(value = "/alluv", method = RequestMethod.GET)
    public R<UserVo> allUvUrl(){
        return new APITemplate<UserVo>(){
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected UserVo process() throws BizException {
                UserVo userVo=new UserVo();
                return userVo;
            }
        }.execute();
    }

}


