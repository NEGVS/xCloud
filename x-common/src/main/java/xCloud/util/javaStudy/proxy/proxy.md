# JDK 动态代理
在 Java 中，JDK 动态代理允许我们在运行时创建代理类，常用于面向切面编程（AOP）等场景。JDK 动态代理通过 `java.lang.reflect.Proxy` 类和 `InvocationHandler` 接口来实现。

下面是一个简单的 JDK 动态代理的代码示例：

### **步骤：**
1. 创建一个接口（被代理对象的接口）。
2. 创建一个实现该接口的类（被代理类）。
3. 创建一个 `InvocationHandler` 接口的实现类，用于处理代理对象的方法调用。
4. 使用 `Proxy.newProxyInstance()` 方法创建代理对象。

### **示例代码：**

#### 1. 定义一个接口 `Person` 和实现类 `PersonImpl`：

```java
public interface Person {
    void sayHello(String name);
}

public class PersonImpl implements Person {
    @Override
    public void sayHello(String name) {
        System.out.println("Hello, " + name);
    }
}
```

#### 2. 创建 `InvocationHandler` 实现类：

```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PersonInvocationHandler implements InvocationHandler {

    private Object target;  // 被代理对象

    public PersonInvocationHandler(Object target) {
        this.target = target;  // 将被代理对象传入
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理方法执行前...");
        Object result = method.invoke(target, args);  // 调用目标方法
        System.out.println("代理方法执行后...");
        return result;
    }
}
```

#### 3. 使用 `Proxy.newProxyInstance()` 创建代理对象：

```java
import java.lang.reflect.Proxy;

public class ProxyExample {

    public static void main(String[] args) {
        // 创建目标对象
        Person person = new PersonImpl();

        // 创建 InvocationHandler
        PersonInvocationHandler handler = new PersonInvocationHandler(person);

        // 创建代理对象
        Person proxyPerson = (Person) Proxy.newProxyInstance(
                person.getClass().getClassLoader(),  // 类加载器
                person.getClass().getInterfaces(),   // 被代理对象实现的接口
                handler                             // InvocationHandler
        );

        // 使用代理对象调用方法
        proxyPerson.sayHello("John");
    }
}
```

### **解释：**

1. **目标接口**：我们定义了一个 `Person` 接口，里面有一个方法 `sayHello()`，`PersonImpl` 是 `Person` 接口的实现类。

2. **`InvocationHandler`**：`PersonInvocationHandler` 实现了 `InvocationHandler` 接口，重写 `invoke()` 方法。这个方法会在调用代理对象的方法时被执行。可以在这里添加日志、权限校验等功能。

3. **创建代理对象**：通过 `Proxy.newProxyInstance()` 方法创建一个代理对象。
    - 第一个参数是类加载器，通常使用目标类的类加载器。
    - 第二个参数是目标对象实现的接口，JDK 动态代理只能代理接口。
    - 第三个参数是 `InvocationHandler`，它定义了代理方法调用的实际处理逻辑。

4. **调用代理方法**：调用代理对象的 `sayHello()` 方法时，会先进入 `invoke()` 方法中，执行自定义的逻辑，然后再调用目标对象的实际方法。

### **运行结果：**

```
代理方法执行前...
Hello, John
代理方法执行后...
```

### **总结：**

- JDK 动态代理通过反射机制创建代理对象。
- 被代理对象必须实现接口，JDK 动态代理不能代理类（如没有接口的类）。
- 代理对象方法调用的前后，可以通过 `InvocationHandler` 来添加自定义逻辑。

### **适用场景：**
- **AOP（面向切面编程）**：可以在方法调用之前和之后执行自定义逻辑，例如日志记录、性能监控、事务管理等。
- **装饰器模式**：可以通过代理增强现有对象的功能。
