package xCloud.util.javaStudy.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *@Description
 *@Author Andy Fan
 *@Date 2025/2/7 10:54
 *@ClassName InvocationHandler
 */
public class PersonInvocationHandler implements InvocationHandler
{

   private Object target;  // 被代理对象

   public PersonInvocationHandler( Object target )
   {
      this.target = target;  // 将被代理对象传入
   }

   @Override
   public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable
   {
      System.out.println( "代理方法执行前..." );
      Object result = method.invoke( target, args );  // 调用目标方法
      System.out.println( "代理方法执行后..." );
      return result;
   }
}
