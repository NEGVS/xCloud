package xCloud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import xCloud.result.XResponseResult;

import java.util.Map;

/**
 *@Description
 *@Author Andy Fan
 *@Date 2025/2/6 09:39
 *@ClassName WebClientService
 */
@Slf4j
public class WebClientService
{
   private final ObjectMapper objectMapper = new ObjectMapper();

   /**
    * 通用的 POST 请求方法
    *
    * @param url         请求的 URL
    * @param contentType 请求的 Content-Type
    * @param requestBody 请求体数据
    * @param <T>         请求体的类型
    * @return 响应结果
    *
    */
   public < T > String sendPostRequest( String url, MediaType contentType, T requestBody )
   {
      // 创建 WebClient 实例
      WebClient webClient = WebClient.builder().defaultHeader( HttpHeaders.CONTENT_TYPE, contentType.toString() ) // 设置默认 Content-Type
            .build();

      try
      {
         // 发送 POST 请求并阻塞等待响应
         String response = webClient.post().uri( url )  // 动态的 URL
               .contentType( contentType )  // 动态的 Content-Type
               .bodyValue( requestBody )  // 请求体数据
               .retrieve().bodyToMono( String.class ).block();  // 阻塞直到响应返回
         // 返回响应成功的 Map
         return objectMapper.writeValueAsString( XResponseResult.success( response ) );

      }
      catch ( Exception e )
      {
         // 记录错误信息并返回失败的 Map
         log.info( "远程调用异常，Error: " + e.getMessage() );
         return null;
      }
   }

   /**
    * 通用的 GET 请求方法，返回 JSON 字符串
    *
    * @param url         请求的 URL
    * @param contentType 请求的 Content-Type
    * @return 响应的 JSON 字符串
    */
   public String sendGetRequest( String url, MediaType contentType ) throws JsonProcessingException
   {
      // 创建 WebClient 实例
      WebClient webClient = WebClient.builder().defaultHeader( HttpHeaders.CONTENT_TYPE, contentType.toString() ) // 设置默认 Content-Type
            .build();

      try
      {
         // 发送 GET 请求并阻塞等待响应
         String response = webClient.get().uri( url )  // 动态的 URL
               .accept( contentType )  // 动态的 Content-Type
               .retrieve().bodyToMono( String.class ).block();  // 阻塞直到响应返回
         // 将响应封装成 JSON 字符串
         return objectMapper.writeValueAsString( XResponseResult.success( response ) );
      }
      catch ( Exception e )
      {
         // 记录错误信息并返回失败的 JSON 字符串
         return objectMapper.writeValueAsString( XResponseResult.fail( e.getMessage() ) );
      }
   }

   /**
    * 创建WebClient
    *
    * @return WebClient
    */
   public static WebClient createWebClient( String mediaType )
   {
      return WebClient.builder().defaultHeader( HttpHeaders.CONTENT_TYPE, mediaType ).build();
   }

   /**
    * POST请求
    *
    * @param url  全路径地址 例如 ： <a href="https://127.0.0.1:8080/test">...</a>
    * @param body 请求体
    * @return 返回的结果（默认是String其实是一个JSON）
    */
   public static String post( String url, Object body )
   {
      WebClient webClient = createWebClient( MediaType.APPLICATION_JSON_VALUE );
      return webClient.post().uri( url ).bodyValue( body ).retrieve().bodyToMono( String.class )
            // 阻塞直到响应返回
            .block();
   }

   /**
    * GET请求
    *
    * @param url         全路径地址 例如 ： <a href="https://127.0.0.1:8080/test">...</a>
    * @param queryParams 请求参数
    * @return 返回的结果（默认是String其实是一个JSON）
    */
   public static String get( String url, Map< String, String > queryParams )
   {
      WebClient webClient = createWebClient( MediaType.APPLICATION_FORM_URLENCODED_VALUE );
      return webClient.get().uri( uriBuilder -> {
               uriBuilder.path( url );
               // 动态设置参数
               queryParams.forEach( uriBuilder::queryParam );
               return uriBuilder.build();
            } ).retrieve().bodyToMono( String.class )
            // 阻塞直到响应返回
            .block();
   }

}
