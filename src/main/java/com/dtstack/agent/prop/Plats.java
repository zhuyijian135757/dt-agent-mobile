package com.dtstack.agent.prop;

import com.dtstack.agent.vo.UserVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @program: dt-mobile-agent
 * @description: 对接平台属性
 * @author: terry.zhu
 * @create: 2019-04-02 14:17
 **/

@Data
public class Plats {

    private Map<String, String> cookieMaps;

    private Map<String, String> platMaps;

    private Map<String, String> expireTimeMaps;

}
