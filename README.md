###应用说明

当前应用为代理服务，为对接平台(datapin，quickbi)提供cookie管理，需要有独立域名，  
与对接平台在同一域下，理论上代理服务于对接平台是一对多的关系。


###部署说明  

* 打包 mvn clean install -Pproduct -U -DskipTest  
* 进入target/目录，找到dt-mobile-agent.tar.gz
* 解压dt-mobile-agent.tar.gz，进入bin，执行脚本命令dt-mobile-agent.sh start

###配置说明(启动前需配置)

*  解压dt-mobile-agent.tar.gz，进入conf, 找到application.yml文件
*  proxy.login: 当前代理服务给认证中心的回调地址
*  plats.cookieMaps: 对接平台(datapin，quickbi)的cookie名称配置
*  plats.platMaps: 代理服务跳转到对接平台(datapin，quickbi)的地址配置
*  plats.expireTimeMaps: 对接平台(datapin，quickbi)会话时间配置
*  newland.loginUrl: 新大陆的认证中心地址
*  newland.authUrl: 新大陆回调信息校验地址
*  newland.sigUserUrl: 新大陆提供的获取单个用户的接口地址
*  newland.allUserUrl: 新大陆提供的获取所有用户的接口地址
