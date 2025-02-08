andy
xCloud Project
模块
1-订单服务 ✅
2-商品服务 ✅
XProductsApplication
3-账户服务
4-库存服务
5-网关服务
6-支付服务
7-通用服务
XCommonApplication

@SpringBootApplication

SpringApplication.run( OrderServiceApplication.class, args );

模块打包注意事项
1-要么有MainApplication文件，2-要么 pom设置 <packaging>pom</packaging>，否则会报错。