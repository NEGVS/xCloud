package xCloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Cloud Gateway 是一个高效、轻量级的 API 网关框架，主要用于处理请求路由、负载均衡、权限校验、限流等功能，
 * 代替了 Zuul 成为 Spring Cloud 推荐的网关解决方案。
 */
@SpringBootApplication
public class XGatewayApplication
{
   public static void main( String[] args )
   {
      System.out.println( "Hello XGatewayApplication !" );
      SpringApplication.run( XGatewayApplication.class, args );
   }
}