package com.dtstack.agent.vo;

import lombok.Data;

import java.util.List;


@Data
public class TenantVo {

    private String departId;

    private String departName;

    private String departLevel;

    private String departType;

    private List<TenantVo> children;

    private List<TenantUserVo> user;

}
