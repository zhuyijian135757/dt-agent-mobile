package com.dtstack.agent.dao;

import com.dtstack.agent.prop.Plats;
import com.dtstack.agent.vo.UserVo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: dt-mobile-agent
 * @description: cookie存储
 * @author: terry.zhu
 * @create: 2019-04-02 16:21
 **/
@Slf4j
@Component
public class CookieDao {

    private Map<String,UserVo> cookieDb= Maps.newConcurrentMap();

    private Map<String,Long> cookieTimeDb= Maps.newConcurrentMap();

    @Autowired
    private Plats plats;

    /**
     *  获取cookie过期时间
     * @param platName
     * @param cookieValue
     * @return
     */
    public Long getCookieExpireTime(String platName,String cookieValue){
        String key=String.format("%s-%s",platName,cookieValue);
        return cookieTimeDb.get(key);
    }

    /**
     *  设置cookie过期时间
     * @param platName
     * @param cookieValue
     * @return
     */
    public void setCookieExpireTime(String platName,String cookieValue,String expireTime){
        String key=String.format("%s-%s",platName,cookieValue);
        cookieTimeDb.put(key,System.currentTimeMillis()+Long.parseLong(expireTime)*1000);
    }

    /**
     * 根据cookie获取用户信息
     * @param platName
     * @param cookieValue
     * @return
     */
    public UserVo getCookie(String platName,String cookieValue){
        String key=String.format("%s-%s",platName,cookieValue);
        log.info("get cookie: {}",key);
        return cookieDb.get(key);
    }

    /**
     * 存入用户信息
     * @param platName
     * @param cookieValue
     * @param userVo
     * @return
     */
    public Boolean putCookie(String platName,String cookieValue,UserVo userVo){
        try{
            String key=String.format("%s-%s",platName,cookieValue);
            String expireTime=plats.getExpireTimeMaps().get(platName);
            log.info("put cookie: {}, uservo: {}",key,userVo.toString());
            cookieDb.put(key,userVo);
            cookieTimeDb.put(key,System.currentTimeMillis()+Long.parseLong(expireTime)*1000);
        }catch (Exception e){
            log.info("存入cookie失败,{},{},{}",platName,cookieValue, e);
            return false;
        }
        return true;
    }

    /**
     * 登出清楚cookie
     * @param platName
     * @param cookieValue
     * @return
     */
    public Boolean expireCookie(String platName,String cookieValue){
        try{
            String key=String.format("%s-%s",platName,cookieValue);
            cookieDb.remove(key);
            cookieTimeDb.remove(key);
        }catch (Exception e){
            log.info("删除cookie失败,{},{},{}",platName,cookieValue, e);
            return false;
        }
        return true;
    }

    /**
     * 更新用户信息
     * @param userId
     * @param userVo
     * @return
     */
    public boolean updateUser(String userId,UserVo userVo){
        try{
            cookieDb.values().stream().forEach( u -> {
                if(u.getUserId().equals(userId)){
                    u=userVo;
                }
            });
        }catch (Exception e){
            log.info("更新用户信息失败,{},{}",userId, e);
            return false;
        }
        return true;
    }


}
