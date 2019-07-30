package com.dtstack.agent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @program: dt-agent-mobile
 * @description: ${description}
 * @author: terry.zhu
 * @create: 2019-05-14 11:09
 **/
@Data
public class UserDto {

    @JsonProperty("oneId")
    private String userId;

    public UserDto() { }

    public UserDto(String userId) {
        this.userId = userId;
    }
}
