package xCloud.entity.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 自定义ResponseResult封装返回结果
 * RestController自动返回json
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Schema(name = "统一结果集处理")
public class XResponseResult< T > implements java.io.Serializable
{
   /**
    * 状态码
    */
   //   @Schema(name = "状态码")
   private Integer code;

   /**
    * 状态信息
    */
   //   @Schema(name = "状态信息")
   private Boolean status;

   /**
    * 返回信息
    */
   //   @Schema(name = "返回信息")
   private String message;

   /**
    * 数据
    */
   //   @Schema(name = "数据")
   private T data;

   /**
    * 全参数方法
    *
    * @param code    状态码
    * @param status  状态
    * @param message 返回信息
    * @param data    返回数据
    * @param <T>     泛型
    * @return {@link XResponseResult <T>}
    */
   private static < T > XResponseResult< T > response( Integer code, Boolean status, String message, T data )
   {
      XResponseResult< T > responseResult = new XResponseResult<>();
      responseResult.setCode( code );
      responseResult.setStatus( status );
      responseResult.setMessage( message );
      responseResult.setData( data );
      return responseResult;
   }

   /**
    * 全参数方法
    *
    * @param code    状态码
    * @param status  状态
    * @param message 返回信息
    * @param <T>     泛型
    * @return {@link XResponseResult <T>}
    */
   private static < T > XResponseResult< T > response( Integer code, Boolean status, String message )
   {
      XResponseResult< T > responseResult = new XResponseResult<>();
      responseResult.setCode( code );
      responseResult.setStatus( status );
      responseResult.setMessage( message );
      return responseResult;
   }

   /**
    * 成功返回（无参）
    *
    * @param <T> 泛型
    * @return {@link XResponseResult <T>}
    */
   public static < T > XResponseResult< T > success()
   {
      return response( HttpStatusEnum.SUCCESS.getCode(), true, HttpStatusEnum.SUCCESS.getMessage(), null );
   }

   /**
    * 成功返回（枚举参数）
    *
    * @param httpResponseEnum 枚举参数
    * @param <T>              泛型
    * @return {@link XResponseResult <T>}
    */
   public static < T > XResponseResult< T > success( HttpStatusEnum httpResponseEnum )
   {
      return response( httpResponseEnum.getCode(), true, httpResponseEnum.getMessage() );
   }

   /**
    * 成功返回（状态码+返回信息）
    *
    * @param code    状态码
    * @param message 返回信息
    * @param <T>     泛型
    * @return {@link XResponseResult <T>}
    */
   public static < T > XResponseResult< T > success( Integer code, String message )
   {
      return response( code, true, message );
   }

   /**
    * 成功返回（返回信息 + 数据）
    *
    * @param message 返回信息
    * @param data    数据
    * @param <T>     泛型
    * @return {@link XResponseResult <T>}
    */
   public static < T > XResponseResult< T > success( String message, T data )
   {
      return response( HttpStatusEnum.SUCCESS.getCode(), true, message, data );
   }

   /**
    * 成功返回（状态码+返回信息+数据）
    *
    * @param code    状态码
    * @param message 返回信息
    * @param data    数据
    * @param <T>     泛型
    * @return {@link XResponseResult <T>}
    */
   public static < T > XResponseResult< T > success( Integer code, String message, T data )
   {
      return response( code, true, message, data );
   }

   /**
    * 成功返回（数据）
    *
    * @param data 数据
    * @param <T>  泛型
    * @return {@link XResponseResult <T>}
    */
   public static < T > XResponseResult< T > success( T data )
   {
      return response( HttpStatusEnum.SUCCESS.getCode(), true, HttpStatusEnum.SUCCESS.getMessage(), data );
   }

   /**
    * 成功返回（返回信息）
    *
    * @param message 返回信息
    * @param <T>  泛型
    * @return {@link XResponseResult <T>}
    */
   public static < T > XResponseResult< T > success( String message )
   {
      return response( HttpStatusEnum.SUCCESS.getCode(), true, message, null );
   }

   /**
    * 失败返回（无参）
    *
    * @param <T> 泛型
    * @return {@link XResponseResult <T>}
    */
   public < T > XResponseResult< T > fail()
   {
      return response( HttpStatusEnum.ERROR.getCode(), false, HttpStatusEnum.ERROR.getMessage(), null );
   }

   /**
    * 失败返回（枚举）
    *
    * @param httpResponseEnum 枚举
    * @param <T>              泛型
    * @return {@link XResponseResult <T>}
    */
   public static < T > XResponseResult< T > fail( HttpStatusEnum httpResponseEnum )
   {
      return response( httpResponseEnum.getCode(), false, httpResponseEnum.getMessage() );
   }

   /**
    * 失败返回（状态码+返回信息）
    *
    * @param code    状态码
    * @param message 返回信息
    * @param <T>     泛型
    * @return {@link XResponseResult <T>}
    */
   public static < T > XResponseResult< T > fail( Integer code, String message )
   {
      return response( code, false, message );
   }

   /**
    * 失败返回（返回信息+数据）
    *
    * @param message 返回信息
    * @param data    数据
    * @param <T>     泛型
    * @return {@link XResponseResult <T>}
    */
   public static < T > XResponseResult< T > fail( String message, T data )
   {
      return response( HttpStatusEnum.ERROR.getCode(), false, message, data );
   }

   /**
    * 失败返回（状态码+返回信息+数据）
    *
    * @param code    状态码
    * @param message 返回消息
    * @param data    数据
    * @param <T>     泛型
    * @return {@link XResponseResult <T>}
    */
   public static < T > XResponseResult< T > fail( Integer code, String message, T data )
   {
      return response( code, false, message, data );
   }

   /**
    * 失败返回（数据）
    *
    * @param data 数据
    * @param <T>  泛型
    * @return {@link XResponseResult <T>}
    */
   public static < T > XResponseResult< T > fail( T data )
   {
      return response( HttpStatusEnum.ERROR.getCode(), false, HttpStatusEnum.ERROR.getMessage(), data );
   }

   /**
    * 失败返回（返回信息）
    *
    * @param message 返回信息
    * @param <T>  泛型
    * @return {@link XResponseResult <T>}
    */
   public static < T > XResponseResult< T > fail( String message )
   {
      return response( HttpStatusEnum.ERROR.getCode(), false, message, null );
   }
}
