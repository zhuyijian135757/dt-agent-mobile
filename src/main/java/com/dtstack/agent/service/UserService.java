package com.dtstack.agent.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dtstack.agent.dao.CookieDao;
import com.dtstack.agent.dto.SsoDto;
import com.dtstack.agent.dto.UserDto;
import com.dtstack.agent.lang.Utils;
import com.dtstack.agent.prop.NewLand;
import com.dtstack.agent.prop.Plats;
import com.dtstack.agent.vo.UrlVo;
import com.dtstack.agent.vo.UserVo;
import com.dtstack.plat.lang.base.JSONs;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

            String newLandUserId=userId.substring(9);
            log.info("oneId:{},userId:{}",userId,newLandUserId);

            URI uri=UriComponentsBuilder.fromUriString(newLand.getSigUserUrl())
                    .build().toUri();
            UserDto userDto=new UserDto(newLandUserId);
            MultiValueMap map=new HttpHeaders();
            List<String> list=new ArrayList<>();
            list.add("application/json");
            map.put("Content-Type",list);
            HttpEntity<UserDto> httpEntity=new HttpEntity<UserDto>(userDto,map);

            ResponseEntity<R<UserVo>> result=restTemplate.exchange(uri, HttpMethod.POST,
                    httpEntity,new ParameterizedTypeReference<R<UserVo>>() {});
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
    public List<UserVo> getAllUser(){
        try{
            URI uri=UriComponentsBuilder.fromUriString(newLand.getAllUserUrl())
                    .build().toUri();
            URL url=new URL(uri.toString());
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.connect();
            OutputStream outputStream=httpURLConnection.getOutputStream();
            outputStream.write("{}".getBytes());
            outputStream.flush();
            outputStream.close();
            int respSt=httpURLConnection.getResponseCode();
            ByteArrayOutputStream ba=new ByteArrayOutputStream();
            InputStream inputStream=httpURLConnection.getInputStream();
            IOUtils.copy(inputStream,ba);
            String message=new String(ba.toByteArray(),"utf-8");
            inputStream.close();
            httpURLConnection.disconnect();

            R<List<UserVo>> resp= JSONs.fromJSON(message, new TypeReference<R<List<UserVo>>>() {});
            log.info("返回响应码:{},msg:{}, 内容:{}",respSt,message,resp.getData());
            if(respSt==HttpStatus.OK.value()){
                return   resp.getData();
            }else {
                throw new BizException(String.format("返回响应码:{}, 内容:{}",respSt,resp.getData()));
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
    public void login(String verifyCode,String randomSeq,String tenantId, String platName,
                      String platUrl, HttpServletRequest request, HttpServletResponse response){
        try{
            URI uri=UriComponentsBuilder.fromUriString(newLand.getAuthUrl())
                    .build().toUri();

            SsoDto ssoDto=new SsoDto(verifyCode,randomSeq,tenantId);
            MultiValueMap map=new HttpHeaders();
            List<String> list=new ArrayList<>();
            list.add("application/json");
            map.put("Content-Type",list);
            HttpEntity<SsoDto> httpEntity=new HttpEntity<SsoDto>(ssoDto,map);

            ResponseEntity<R<UserVo>> result=restTemplate.exchange(uri, HttpMethod.POST,
                    httpEntity,new ParameterizedTypeReference<R<UserVo>>() {});
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
    public UrlVo logout(String platName,String cookieValue){
        boolean result= cookieDao.expireCookie(platName,cookieValue);
        if(!result){
            throw new BizException("登出失败");
        }else{
            UrlVo url=new UrlVo();
            url.setRedirect(baseService.getNewLandLoginUrl(platName, null));
            return url;
        }
    }

}
