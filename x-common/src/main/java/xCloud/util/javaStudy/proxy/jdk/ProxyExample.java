package xCloud.util.javaStudy.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 *@Description
 *@Author Andy Fan
 *@Date 2025/2/7 10:57
 *@ClassName ProxyExample
 * 解释：
 * 目标接口：我们定义了一个 Person 接口，里面有一个方法 sayHello()，PersonImpl 是 Person 接口的实现类。
 *
 * InvocationHandler：PersonInvocationHandler 实现了 InvocationHandler 接口，重写 invoke() 方法。这个方法会在调用代理对象的方法时被执行。可以在这里添加日志、权限校验等功能。
 *
 * 创建代理对象：通过 Proxy.newProxyInstance() 方法创建一个代理对象。
 *
 * 第一个参数是类加载器，通常使用目标类的类加载器。
 * 第二个参数是目标对象实现的接口，JDK 动态代理只能代理接口。
 * 第三个参数是 InvocationHandler，它定义了代理方法调用的实际处理逻辑。
 * 调用代理方法：调用代理对象的 sayHello() 方法时，会先进入 invoke() 方法中，执行自定义的逻辑，然后再调用目标对象的实际方法。
 *
 * 总结：
 * JDK 动态代理通过反射机制创建代理对象。
 * 被代理对象必须实现接口，JDK 动态代理不能代理类（如没有接口的类）。
 * 代理对象方法调用的前后，可以通过 InvocationHandler 来添加自定义逻辑。
 * 适用场景：
 * AOP（面向切面编程）：可以在方法调用之前和之后执行自定义逻辑，例如日志记录、性能监控、事务管理等。
 * 装饰器模式：可以通过代理增强现有对象的功能。
 */
public class ProxyExample
{
   public static void main( String[] args )
   {
      // 创建目标对象
      Person person = new PersonImpl();

      // 创建 InvocationHandler
      PersonInvocationHandler handler = new PersonInvocationHandler( person );

      // 创建代理对象
      Person proxyPerson = ( Person ) Proxy.newProxyInstance( person.getClass().getClassLoader(),  // 类加载器
            person.getClass().getInterfaces(),   // 被代理对象实现的接口
            handler                             // InvocationHandler
      );

      // 使用代理对象调用方法
      proxyPerson.sayHello( "John" );
   }
}
