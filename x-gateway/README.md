andy
xCloud Project
Spring Cloud Gateway 是 Spring Cloud 生态系统中的一个 API 网关组件，用于构建微服务架构中的网关服务。它可以实现路由转发、负载均衡、熔断、限流、鉴权等功能。以下是 Spring Cloud Gateway 的基本使用方法：

---

### **1. 添加依赖**
在 `pom.xml` 中添加 Spring Cloud Gateway 的依赖：
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

如果需要与其他组件（如 Eureka、Nacos）集成，还需添加对应的依赖：
- Eureka 客户端：
  ```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>
  ```
- Nacos 客户端：
  ```xml
  <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
  </dependency>
  ```

---

### **2. 配置文件**
在 `application.yml` 或 `application.properties` 中配置 Gateway 的路由规则和其他功能。

#### 示例配置（基于路径的路由）：
```yaml
server:
  port: 8080  # Gateway 服务端口

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: user-service  # 路由 ID
          uri: http://localhost:8081  # 目标服务地址
          predicates:
            - Path=/user/**  # 匹配路径
          filters:
            - StripPrefix=1  # 去掉前缀（去掉 /user）
        - id: order-service
          uri: http://localhost:8082
          predicates:
            - Path=/order/**
          filters:
            - StripPrefix=1
```

#### 示例配置（基于服务发现的路由）：
如果使用了服务发现组件（如 Eureka 或 Nacos），可以通过服务名称进行路由：
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service  # lb:// 表示从服务发现中获取服务地址
          predicates:
            - Path=/user/**
          filters:
            - StripPrefix=1
        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/order/**
          filters:
            - StripPrefix=1
```

---

### **3. 启动类**
在 Spring Boot 应用的启动类上添加 `@EnableDiscoveryClient`（如果使用了服务发现组件）：
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
```

---

### **4. 常用功能**

#### **(1) 路由规则**
- **Path 路由**：根据请求路径匹配路由。
  ```yaml
  predicates:
    - Path=/user/**
  ```
- **Host 路由**：根据请求的 Host 匹配路由。
  ```yaml
  predicates:
    - Host=**.example.com
  ```
- **Method 路由**：根据请求方法匹配路由。
  ```yaml
  predicates:
    - Method=GET,POST
  ```

#### **(2) 过滤器**
- **StripPrefix**：去掉请求路径的前缀。
  ```yaml
  filters:
    - StripPrefix=1
  ```
- **AddRequestHeader**：添加请求头。
  ```yaml
  filters:
    - AddRequestHeader=X-Request-Foo, Bar
  ```
- **AddResponseHeader**：添加响应头。
  ```yaml
  filters:
    - AddResponseHeader=X-Response-Foo, Bar
  ```
- **Retry**：重试机制。
  ```yaml
  filters:
    - name: Retry
      args:
        retries: 3
        statuses: BAD_GATEWAY
  ```

#### **(3) 全局过滤器**
可以通过实现 `GlobalFilter` 接口定义全局过滤器：
```java
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 自定义逻辑
        System.out.println("Global filter executed");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // 过滤器执行顺序
    }
}
```

---

### **5. 高级功能**

#### **(1) 限流**
使用 `RequestRateLimiter` 过滤器实现限流：
```yaml
filters:
  - name: RequestRateLimiter
    args:
      redis-rate-limiter.replenishRate: 10  # 每秒允许的请求数
      redis-rate-limiter.burstCapacity: 20  # 最大突发请求数
      key-resolver: "#{@userKeyResolver}"   # 限流键解析器
```

定义 `KeyResolver` Bean：
```java
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
```

#### **(2) 熔断**
使用 `Hystrix` 过滤器实现熔断：
```yaml
filters:
  - name: Hystrix
    args:
      name: fallbackCmd
      fallbackUri: forward:/fallback
```

定义 Fallback 端点：
```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/fallback")
    public String fallback() {
        return "Service is unavailable. Please try again later.";
    }
}
```

---

### **6. 测试**
启动 Gateway 服务后，可以通过以下方式测试：
- 访问 `http://localhost:8080/user/xxx`，请求会被转发到 `user-service`。
- 访问 `http://localhost:8080/order/xxx`，请求会被转发到 `order-service`。

---

### **总结**
Spring Cloud Gateway 是一个功能强大的 API 网关，支持路由、过滤、限流、熔断等功能。通过配置文件或代码，可以灵活地实现各种网关需求。如果有更多问题，欢迎随时提问！ 😊
