package com.dtstack.agent.service;

import com.dtstack.agent.dao.CookieDao;
import com.dtstack.agent.lang.Utils;
import com.dtstack.agent.prop.NewLand;
import com.dtstack.agent.prop.Plats;
import com.dtstack.agent.vo.UserVo;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

/**
 * @program: dt-mobile-agent
 * @description: 用户业务层
 * @author: terry.zhu
 * @create: 2019-04-02 11:50
 **/
@Slf4j
@Service
public class UserService {

    @Autowired
    private Plats plats;

    @Autowired
    private NewLand newLand;

    @Autowired
    private BaseService baseService;

    @Autowired
    private CookieDao cookieDao;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取单个用户信息
     * @param userId
     * @return
     */
    public UserVo getUser(String userId){
        try{
            URI uri=UriComponentsBuilder.fromUriString(newLand.getSigUserUrl())
                    .queryParam("userId",userId)
                    .build().toUri();
            ResponseEntity<R<UserVo>> result=restTemplate.exchange(uri, HttpMethod.GET,
                    HttpEntity.EMPTY,new ParameterizedTypeReference<R<UserVo>>() {});
            if(result.getStatusCodeValue()==HttpStatus.OK.value()){
                return   result.getBody().getData();
            }else {
                throw new BizException(String.format("返回响应码:{}, 内容:{}",result.getStatusCodeValue(),result.getBody()));
            }
        }catch(Exception e){
            log.info("获取用户信息失败",e);
            throw new BizException(e);
        }
    }


    /**
     * 获取所有用户信息
     * @return
     */
    public Object getAllUser(){
        try{
            URI uri=UriComponentsBuilder.fromUriString(newLand.getAllUserUrl())
                    .build().toUri();
            ResponseEntity<R<Object>> result=restTemplate.exchange(uri, HttpMethod.GET,
                    HttpEntity.EMPTY,new ParameterizedTypeReference<R<Object>>() {});
            if(result.getStatusCodeValue()==HttpStatus.OK.value()){
                return   result.getBody().getData();
            }else {
                throw new BizException(String.format("返回响应码:{}, 内容:{}",result.getStatusCodeValue(),result.getBody()));
            }
        }catch(Exception e){
            log.info("获取用户信息失败",e);
            throw new BizException(e);
        }
    }

    /**
     * 更新用户信息
     * @param userId
     * @return
     */
    public String updateUser(String userId){
        if(cookieDao.updateUser(userId,new UserVo())){
            throw new BizException("更新用户信息失败！");
        }
        return "";
    }

    /**
     * 认证系统回调登陆组装
     * @param verifyCode
     * @param platName
     * @param platUrl
     * @param response
     */
    public void login(String verifyCode,String randomSeq, String platName, String platUrl, HttpServletRequest request, HttpServletResponse response){
        try{
            URI uri=UriComponentsBuilder.fromUriString(newLand.getAuthUrl())
                    .queryParam("verifyCode",verifyCode).queryParam("randomSeq",randomSeq)
                    .build().toUri();
            ResponseEntity<R<UserVo>> result=restTemplate.exchange(uri, HttpMethod.GET,
                    HttpEntity.EMPTY,new ParameterizedTypeReference<R<UserVo>>() {});
            if(result.getStatusCodeValue()==HttpStatus.OK.value()){
                String cookieValue=Utils.getRandomCookie();
                String cookieName=plats.getCookieMaps().get(platName);
                cookieDao.putCookie(platName,cookieValue,result.getBody().getData());

                String addr=Utils.getMajorDomain(request);
                Cookie ck=new Cookie(cookieName,cookieValue);
                ck.setMaxAge(Integer.MAX_VALUE);
                ck.setPath("/");
                ck.setDomain(addr);

                response.addCookie(ck);
                response.setStatus(302);
                response.setHeader("location",platUrl);
            }else {
                throw new BizException(String.format("返回响应码:{}, 内容:{}",result.getStatusCodeValue(),result.getBody()));
            }
        }catch(Exception e){
            log.info("认证中心回调失败 {}",e);
            throw new BizException(e);
        }
    }


    /**
     * 登出清除cookie
     * @param platName
     * @param cookieValue
     * @return
     */
    public String logout(String platName,String cookieValue){
        boolean result= cookieDao.expireCookie(platName,cookieValue);
        if(!result){
            throw new BizException("登出失败");
        }else{
            return baseService.getNewLandLoginUrl(platName);
        }
    }

}
