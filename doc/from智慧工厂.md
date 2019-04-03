
###- 单点认证接口

**接口说明：**
- 浏览器接受到认证系统重定向请求后，向对接系统(datapin,quickbi等)发起的回调请求，用于认证获取用户信息

**请求URL：** 
- ` http://ip:port/path?verifyCode=LNdOmlfSO9j451VU5JvQ5oB0QyzLX6oUD7q4Y1CMJL0dfD0bkPtqxeL4XXuVzOrH&randomSeq=R9g5VTafVOur28C9DsvBmW60xdVzt9Zb5b3LAtZbVIH8hLMHQI1SqWdVycwsr7Gn `
  
**请求方式：** 
- GET 

**内容类型：**
- Content-Type: application/json;charset=UTF-8


**请求参数：** 

``` 
    "verifyCode": "LNdOmlfSO9j451VU5JvQ5oB0QyzLX6oUD7q4Y1CMJL0dfD0bkPtqxeL4XXuVzOrH", 
    "randomSeq": "R9g5VTafVOur28C9DsvBmW60xdVzt9Zb5b3LAtZbVIH8hLMHQI1SqWdVycwsr7Gn"

```
**内容说明：**   
- verifyCode：认证系统回调提供  
- randomSeq:  对接系统随机生产64位数据  
- 具体单点登录流程图如下:   
-  ![](https://zjyy-archive.oss-cn-shanghai.aliyuncs.com/sso.png) 
 

 **返回示例**

``` 
{
    "success": true,
    "message": "登陆成功",
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
- ` http://ip:port/path`
  
**请求方式：** 
- GET

**内容类型：**
- Content-Type: application/json;charset=UTF-8


 **返回示例**

``` 
{
    "success": true,
    "message": "",
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
                       "isAdmin": "是否是租户管理员"
                    },
                    {
                       "userId": "1235",
                       "userName": "李三",
                       "nickName" : "李三",
                       "mobilePhone": "13888888888",
                       "mailAddr": "123@163.com",
                       "isAdmin": "是否是租户管理员"
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

###- 单个用户信息列表获取接口

**接口说明：**
- 获取单个用户信息

**请求URL：** 
- ` http://ip:port/path`
  
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
    "message": "登陆成功",
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


