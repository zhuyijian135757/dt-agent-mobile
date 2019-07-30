package com.dtstack.agent.dto;

import lombok.Data;

/**
 * @program: dt-agent-mobile
 * @description: ${description}
 * @author: terry.zhu
 * @create: 2019-05-14 11:10
 **/
@Data
public class SsoDto {

    private String verifyCode;

    private String randomSeq;

    private String tenantId;

    public SsoDto() { }

    public SsoDto(String verifyCode, String randomSeq) {
        this.verifyCode = verifyCode;
        this.randomSeq = randomSeq;
    }

    public SsoDto(String verifyCode, String randomSeq, String tenantId) {
        this.verifyCode = verifyCode;
        this.randomSeq = randomSeq;
        this.tenantId = tenantId;
    }
}
