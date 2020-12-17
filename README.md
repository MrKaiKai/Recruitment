# 基于OKhttp的快递物流查询

## OKhttp介绍

项目开源地址 ：https://github.com/square/okhttp
项目使用：在build.gradle中加入依赖 compile 'com.squareup.okhttp3:okhttp:3.0.1'

OkHttp是一个高效的HTTP库:
1.支持 SPDY ，共享同一个Socket来处理同一个服务器的所有请求
2.如果SPDY不可用，则通过连接池来减少请求延时
3.无缝的支持GZIP来减少数据流量
4.缓存响应数据来减少重复的网络请求
	
## 物流查询接口

```
https://www.bejson.com/knownjson/webInterface/
```

	经测该改接口有时候不准