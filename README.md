andy
xCloud Project

├── x-ui              // 前端框架 [80]
├── x-gateway         // 网关模块 [8080]
├── x-auth            // 认证中心 [9200]
├── x-api             // 接口模块
│       └── x-api-system                          // 系统接口
├── x-common          // 通用模块
│       └── x-common-core                         // 核心模块
│       └── x-common-datascope                    // 权限范围
│       └── x-common-datasource                   // 多数据源
│       └── x-common-log                          // 日志记录
│       └── x-common-redis                        // 缓存服务
│       └── x-common-seata                        // 分布式事务
│       └── x-common-security                     // 安全模块
│       └── x-common-swagger                      // 系统接口
├── x-modules         // 业务模块
│       └── x-system                              // 系统模块 [9201]
│       └── x-gen                                 // 代码生成 [9202]
│       └── x-job                                 // 定时任务 [9203]
│       └── x-file                                // 文件服务 [9300]
├── x-visual          // 图形化管理模块
│       └── x-visual-monitor                      // 监控中心 [9100]
├──pom.xml                // 公共依赖

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