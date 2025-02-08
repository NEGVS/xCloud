package xCloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDiscoryClient
//@EnableDiscoveryClient 是 Spring Cloud 中的一个注解，用于将微服务注册到服务发现组件（如 Eureka、Consul、Zookeeper 或 Nacos）中。通过这个注解，你的微服务可以向服务注册中心注册自己，并能够从注册中心发现其他服务。
public class XUserApplication
{
   public static void main( String[] args )
   {
      SpringApplication.run( XUserApplication.class, args );
   }
}