package com.dtstack.agent.controller;

import com.dtstack.agent.dto.SsoDto;
import com.dtstack.agent.lang.Api;
import com.dtstack.agent.vo.NewLandUserVo;
import com.dtstack.agent.vo.UserVo;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public R<NewLandUserVo> auth(@RequestBody SsoDto ssoDto){
        return new APITemplate<NewLandUserVo>(){
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected NewLandUserVo process() throws BizException {
                NewLandUserVo userVo=new NewLandUserVo();
                userVo.setUserId("110");
                userVo.setOneId("1110sb");
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
    @RequestMapping(value = "/siguv", method = RequestMethod.POST)
    public R<NewLandUserVo> sigUserUrl(@RequestBody Map<String,String> map){
        return new APITemplate<NewLandUserVo>(){
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected NewLandUserVo process() throws BizException {
                NewLandUserVo userVo=new NewLandUserVo();
                userVo.setUserId("1101");
                userVo.setOneId("1110sa");
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
    @RequestMapping(value = "/alluv", method = RequestMethod.POST)
    public R<List<NewLandUserVo>> allUvUrl(){
        return new APITemplate<List<NewLandUserVo>>(){
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<NewLandUserVo> process() throws BizException {
                List<NewLandUserVo> ll= Lists.newArrayList();
                NewLandUserVo userVo=new NewLandUserVo();
                userVo.setUserId("2101");
                userVo.setOneId("2110sa");
                userVo.setUserName("2rose");
                userVo.setOrgId("211");
                userVo.setOrgName("大数据中心1");
                userVo.setHomeCounty("zj");
                userVo.setHomeCountyName("浙江省");
                userVo.setHomeCity("hz");
                userVo.setHomeCityName("杭州");
                userVo.setMailAddr("123213@133.com");
                userVo.setMobilePhone("13131313");
                userVo.setNickName("匿名1");
                ll.add(userVo);
                return ll;
            }
        }.execute();
    }

}


