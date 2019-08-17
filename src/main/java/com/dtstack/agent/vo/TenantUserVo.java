package com.dtstack.agent.vo;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

/**
 * @program: dt-agent-mobile
 * @description: ${description}
 * @author: terry.zhu
 * @create: 2019-08-13 17:18
 **/
@Data
public class TenantUserVo {


    @JsonSetter("oneId")
    private String userId;

    private String userName;

    private String nickName;

    private String mobilePhone;

    private String mailAddr;

    private String isAdmin;

    private String isBraMger;

    private String isSenMger;

    private String isMetaMger;

    private String isDataMger;

    private String isDataOperator;

    private String isMaintainOperator;

    private String isDevOperator;



}
