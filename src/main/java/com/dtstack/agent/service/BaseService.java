package com.dtstack.agent.service;

import com.dtstack.agent.prop.NewLand;
import com.dtstack.agent.prop.Plats;
import com.dtstack.agent.prop.Proxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @program: dt-mobile-agent
 * @description: 基础业务层
 * @author: terry.zhu
 * @create: 2019-04-02 20:59
 **/
@Service
public class BaseService {

    @Autowired
    private Plats plats;

    @Autowired
    private NewLand newLand;

    @Autowired
    private Proxy proxy;

    /**
     * 组装认证中心登陆地址，带上回调地址
     * @param platName
     * @return
     */
    public String getNewLandLoginUrl(String platName){
        String platUrl=plats.getPlatMaps().get(platName);
        String proxyUrl=UriComponentsBuilder.fromUriString(proxy.getLoginUrl())
                .queryParam("platName",platName).queryParam("targetUri",platUrl).toUriString();
        String newLandLoginUrl=UriComponentsBuilder.fromUriString(newLand.getLoginUrl())
                .queryParam("targetUri",proxyUrl).toUriString();
        return newLandLoginUrl;
    }


}
