package xCloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 *@Description
 *@Author Andy Fan
 *@Date 2025/1/15 17:21
 *@ClassName
 * RestTemplateConfig 实现思路: order-service 服务向 product-service 服务发送⼀个 http 请求, 把得到的返回结果, 和订单结果融合在⼀起, 返回给调用方
 *
 * 实现方式: 采用 Spring 提供的 RestTemplate
 */
@Configuration
public class RestTemplateConfig
{
   @Bean
   public RestTemplate restTemplate()
   {
      return new RestTemplate();
   }
}
