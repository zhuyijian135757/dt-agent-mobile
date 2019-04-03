package com.dtstack.agent.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: dt-mobile-agent
 * @description: ${description}
 * @author: terry.zhu
 * @create: 2019-04-02 11:46
 **/
@Slf4j
@Data
public class UserVo {

    private String userId;

    private String userName;

    private String nickName;

    private String orgId;

    private String orgName;

    private String homeCity;

    private String homeCityName;

    private String homeCounty;

    private String homeCountyName;

    private String mobilePhone;

    private String mailAddr;

}
