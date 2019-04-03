package com.dtstack.agent.controller;

import com.dtstack.agent.lang.Api;
import com.dtstack.agent.service.CookieService;
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

/**
 * @program: dt-mobile-agent
 * @description: cookie rest接口
 * @author: terry.zhu
 * @create: 2019-04-02 11:22
 **/

@RestController
@RequestMapping(Api.API_PREFIX + "/cookie")
public class CookieController {

    @Autowired
    private CookieService cookieService;


    /**
     * 根据平台名称获取不同平台下的cookie名称
     * @param platName
     * @return
     */
    @RequestMapping(value = "/getPlatCookieName", method = RequestMethod.GET)
    public R<String> getPlatName(@RequestParam("platName") String platName){
        return new APITemplate<String>(){

            @Override
            protected void checkParams() throws IllegalArgumentException {
                Assert.notNull(platName,"平台名称不能为空");
            }

            @Override
            protected String process() throws BizException {
                return cookieService.getPlatCookieName(platName);
            }
        }.execute();
    }

    /**
     * 获取不同平台的单个会话的持续时间
     * @param platName
     * @return
     */
    @RequestMapping(value = "/getExpireTime", method = RequestMethod.GET)
    public R<String> getExpireTime(@RequestParam("platName") String platName){
        return new APITemplate<String>(){

            @Override
            protected void checkParams() throws IllegalArgumentException {
                Assert.notNull(platName,"平台名称不能为空");
            }

            @Override
            protected String process() throws BizException {
                return cookieService.getExpireTime(platName);
            }
        }.execute();
    }

    /**
     * 检验cookie有效性
     * @param platName
     * @param cookieValue
     * @return
     */
    @RequestMapping(value = "/validCookie", method = RequestMethod.GET)
    public R<Object> validCookie(@RequestParam("platName") String platName,
                                 @RequestParam("cookieValue") String cookieValue,
                                 HttpServletRequest request){
        return cookieService.validCookie(platName,cookieValue,request);
    }


}
