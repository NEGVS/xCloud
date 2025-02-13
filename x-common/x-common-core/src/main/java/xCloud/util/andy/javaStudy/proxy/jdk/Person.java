package xCloud.util.andy.javaStudy.proxy.jdk;

/**
 * 在 Java 中，JDK 动态代理允许我们在运行时创建代理类，常用于面向切面编程（AOP）等场景。JDK 动态代理通过 java.lang.reflect.Proxy 类和 InvocationHandler 接口来实现。
 *
 * 下面是一个简单的 JDK 动态代理的代码示例：
 *
 * 步骤：
 * 创建一个接口（被代理对象的接口）。
 * 创建一个实现该接口的类（被代理类）。
 * 创建一个 InvocationHandler 接口的实现类，用于处理代理对象的方法调用。
 * 使用 Proxy.newProxyInstance() 方法创建代理对象。
 */
//1. 定义一个接口 Person 和实现类 PersonImpl：
public interface Person
{
   void sayHello( String name );
}


