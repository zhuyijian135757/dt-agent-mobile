
###- 获取cookie名称接口

**请求URL：** 
- ` http://ip:port/api/v1/cookie/getPlatCookieName`
  
**请求方式：** 
- GET 

**内容类型：**
- Content-Type: application/json;charset=UTF-8


**请求参数：** 
``` 
    "platName": "dataPin"
```

**参数说明：**   
- platName：平台名称，quickBi或dataPin

 **返回示例**

``` 
{
    "success": true,
    "data": {
    	"cookieName" : "dpc"
    }
}
```  
<br>
<br>


###- 获取cookie会话时间接口

**请求URL：** 
- ` http://ip:port/api/v1/cookie/getExpireTime`
  
**请求方式：** 
- GET 

**内容类型：**
- Content-Type: application/json;charset=UTF-8


**请求参数：** 
``` 
    "platName": "dataPin"
```

**参数说明：**   
-platName：平台名称

 **返回示例**

``` 
{
    "success": true,
    "data": {
    	"expireTime" : "3600"  //单位秒
    }
}
```  
<br>
<br>


###- 校验cookie接口

**请求URL：** 
- ` http://ip:port/api/v1/cookie/validCookie`
  
**请求方式：** 
- GET 

**内容类型：**
- Content-Type: application/json;charset=UTF-8


**请求参数：** 
``` 
	 "platName": "dataPin"
    "cookieValue": "898e1d77-204d-4e3b-8043-e3cfe46e2610"
```

**参数说明：**   
-cookieValue：cookie值 
-platName：平台名称

 **返回示例**

``` 
{
    "success": true,
    "data": {
    	"user": {
	    	"userId" : "111",
	    	"userName" : "张三",
	    	"nickName" : "张三",
	    	"orgId" : "机构ID",
	    	"orgName" : "机构名称",
		   	"homeCity" : "归属市ID",
	   		"homeCityName" : "归属市名称",
	   		"homeCounty": "归属县ID",
	   		"homeCountyName": "归属县名称",
	   		"mobilePhone": "手机号",
	   		"mailAddr": "邮件地址"
    	}
    }
}

{
    "success": false,
    "data": {
    	"redirect": "http://ip:port/ca?target-uri=http://ip:port/page/proxy?http://ip:port/datapin"
    }
}

redirect回调顺序: 认证中心--->代理服务--->对接系统(dataPin,quickBi)

```  
<br>
<br>


###- 单个用户信息列表获取接口

**接口说明：**
- 获取单个用户信息

**请求URL：** 
- ` http://ip:port/api/v1/user/getUser`
  
**请求方式：** 
- GET

**内容类型：**
- Content-Type: application/json;charset=UTF-8

**请求参数：** 

``` 
    "userId": "111"
```

 **返回示例**

``` 
{
    "success": true,
    "data": {
    	"userId" : "111",
    	"userName" : "张三",
    	"nickName" : "张三",
    	"orgId" : "机构ID",
    	"orgName" : "机构名称",
	   	"homeCity" : "归属市ID",
   		"homeCityName" : "归属市名称",
   		"homeCounty": "归属县ID",
   		"homeCountyName": "归属县名称",
   		"mobilePhone": "手机号",
   		"mailAddr": "邮件地址"
    }
}
```  
<br>
<br>

###- 所有用户信息列表获取接口

**接口说明：**
- 获取各级租户下人员列表，用于审批流

**请求URL：** 
- ` http://ip:port/api/v1/user/getAllUser`
  
**请求方式：** 
- GET

**内容类型：**
- Content-Type: application/json;charset=UTF-8


 **返回示例**

``` 
{
    "success": true,
    "data": {
        "departId": "省公司ID",
        "departName": "省公司",
        "children": [
            {
                "departId": "大数据中心ID",
                "departName": "大数据中心",
                "children": [
                    {
                       "userId": "1234",
                       "userName": "张三",
                       "nickName" : "张三",
                       "mobilePhone" : "13888888888",
                       "mailAddr": "123@163.com",
                       "isAdmin": "是否是租户管理员 0否 1是"
                    },
                    {
                       "userId": "1235",
                       "userName": "李三",
                       "nickName" : "李三",
                       "mobilePhone": "13888888888",
                       "mailAddr": "123@163.com",
                       "isAdmin": "是否是租户管理员 0否 1是"
                    }
                ]
            },
            {
                "departId": "财务部ID",
                "departName": "财务部",
                "children": [
                    
                ]
            }
        ]
    }
}
```  

> PS：层级结构树结合实际情况
<br>
<br>

###- 通知更新用户信息接口

**请求URL：** 
- ` http://ip:port/api/v1/user/updateUser`
  
**请求方式：** 
- GET 

**内容类型：**
- Content-Type: application/json;charset=UTF-8


**请求参数：** 
``` 
    "userId": "110"
```

**参数说明：**   
- userId：用户id

 **返回示例**

``` 
{
    "success": true
}
```  
<br>
<br>


###- 登出接口

**请求URL：** 
- ` http://ip:port/api/v1/user/logout`
  
**请求方式：** 
- GET 

**内容类型：**
- Content-Type: application/json;charset=UTF-8


**请求参数：** 
``` 
	"platName": "dataPin"
	"cookieValue": "898e1d77-204d-4e3b-8043-e3cfe46e2610"
```

**参数说明：**   
- cookieValue：cookie值  
- platName：平台名称


 **返回示例**

``` 
{
    "success": false
}

{
    "success": true,
    "data": {
    	"redirect": "http://ip:port/ca?target-uri=http://ip:port/page/proxy?http://ip:port/datapin"
    }
}

redirect回调顺序: 认证中心--->代理服务--->对接系统(dataPin,quickBi)

```  
<br>
<br>

