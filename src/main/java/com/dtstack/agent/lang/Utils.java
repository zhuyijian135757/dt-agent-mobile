package com.dtstack.agent.lang;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @program: dt-mobile-agent
 * @description: cookie工具类
 * @author: terry.zhu
 * @create: 2019-04-03 10:45
 **/
public class Utils {


    /**
     * 获取随机cookie值，待优化
     * @todo
     * @return
     */
    public static String getRandomCookie(){
        String uuid=UUID.randomUUID().toString().replace("-","");
        return uuid;
    }


    /**
     * 获取随机64位随机数
     * @todo
     * @return
     */
    public static String getRandomSeq(){
        String uuid=UUID.randomUUID().toString().replace("-","");
        return uuid;
    }

    /**
     * 获取主域名，如果是ip直接返回
     * @param request
     * @return
     */
    public static String getMajorDomain(HttpServletRequest request){
        String server=request.getServerName();
        if(ipValid(server)){
            return server;
        }else{
            String domains[]=server.split("\\.");
            if(domains.length>1){
                return domains[domains.length-2]+"."+domains[domains.length-1];
            }else{
                return server;
            }
        }
    }

    /**
     * 是否是ip地址
     * @param ip
     * @return
     */
    public static boolean ipValid(String ip){
        String ipReg = "^(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.)(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.){2}([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))$";
        Pattern ipPattern = Pattern.compile(ipReg);
        return   ipPattern.matcher(ip).matches();
    }

}
