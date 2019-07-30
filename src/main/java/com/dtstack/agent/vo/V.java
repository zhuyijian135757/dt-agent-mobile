package com.dtstack.agent.vo;

import lombok.Data;

import java.util.List;

/**
 * @program: dt-agent-mobile
 * @description: ${description}
 * @author: terry.zhu
 * @create: 2019-07-29 22:44
 **/
@Data
public class V {

    private boolean success;
    private String message;
    private List<UserVo> data;
}
