package com.dtstack.agent.service;

import com.dtstack.agent.dao.CookieDao;
import com.dtstack.agent.prop.Plats;
import com.dtstack.agent.vo.UserVo;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * @program: dt-mobile-agent
 * @description: cookie业务层
 * @author: terry.zhu
 * @create: 2019-04-02 11:50
 **/
@Service
public class CookieService {

    @Autowired
    private Plats plats;

    @Autowired
    private CookieDao cookieDao;

    @Autowired
    private BaseService baseService;

    /**
     * 根据平台名称获取不同平台下的cookie名称,cookie名称从配置文件里获取
     * @param platName
     * @return
     */
    public String getPlatCookieName(String platName){
        String  platCookieName=plats.getCookieMaps().get(platName);
        if(!StringUtils.isEmpty(platCookieName)){
            return platCookieName;
        }else {
            throw new BizException(platName+" 平台不存在!");
        }
    }



    /**
     * 获取不同平台的单个会话的持续时间,从配置文件里获取
     * @param platName
     * @return
     */
    public String getExpireTime(String platName){
        String  platExpireTime=plats.getExpireTimeMaps().get(platName);
        if(!StringUtils.isEmpty(platExpireTime)){
            return platExpireTime;
        }else {
            throw new BizException(platName+" 平台不存在!");
        }
    }

    /**
     * 检验cookie有效性，成功返回用户信息，失败返回重定向地址
     * @param platName
     * @param cookieValue
     * @return
     */
    public R<Object> validCookie(String platName,String cookieValue,HttpServletRequest request){
        UserVo uv= cookieDao.getCookie(platName,cookieValue);
        Long expireTime= cookieDao.getCookieExpireTime(platName,cookieValue);
        if(uv==null || expireTime<System.currentTimeMillis()){
            String newLandLoginUrl=baseService.getNewLandLoginUrl(platName);
            return R.fail().setData(newLandLoginUrl);
        }else{
            String  platExpireTime=plats.getExpireTimeMaps().get(platName);
            cookieDao.setCookieExpireTime(platName,cookieValue,platExpireTime);
            return R.ok().setData(uv);
        }
    }


}
