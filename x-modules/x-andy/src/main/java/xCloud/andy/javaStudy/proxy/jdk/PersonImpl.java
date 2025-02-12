package xCloud.andy.javaStudy.proxy.jdk;

/**
 *@Description
 *@Author Andy Fan
 *@Date 2025/2/7 10:53
 *@ClassName PersonImpl
 */
public class PersonImpl implements Person
{
   @Override
   public void sayHello(String name) {
      System.out.println("Hello, " + name);
   }
}
