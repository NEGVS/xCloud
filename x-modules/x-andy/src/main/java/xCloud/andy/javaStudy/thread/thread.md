在 Java 中，创建线程的三种方式分别是：
1. **继承 `Thread` 类**。
2. **实现 `Runnable` 接口**。
3. **实现 `Callable` 接口**。

以下是每种方式的详细说明和示例代码：

---

### **1. 继承 `Thread` 类**
通过继承 `Thread` 类并重写 `run()` 方法来创建线程。

#### **示例代码**
```java
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("线程运行中: " + Thread.currentThread().getName());
    }
}

public class ThreadExample {
    public static void main(String[] args) {
        // 创建线程对象
        MyThread thread = new MyThread();
        
        // 启动线程
        thread.start();
    }
}
```

**输出**：
```
线程运行中: Thread-0
```

**特点**：
- 简单直接，适合简单的线程任务。
- 由于 Java 是单继承，继承 `Thread` 类后无法再继承其他类。

---

### **2. 实现 `Runnable` 接口**
通过实现 `Runnable` 接口并实现 `run()` 方法来创建线程。

#### **示例代码**
```java
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("线程运行中: " + Thread.currentThread().getName());
    }
}

public class RunnableExample {
    public static void main(String[] args) {
        // 创建 Runnable 对象
        MyRunnable myRunnable = new MyRunnable();
        
        // 创建线程对象
        Thread thread = new Thread(myRunnable);
        
        // 启动线程
        thread.start();
    }
}
```

**输出**：
```
线程运行中: Thread-0
```

**特点**：
- 更灵活，因为 Java 支持多接口实现。
- 适合需要共享资源的场景（多个线程可以共享同一个 `Runnable` 对象）。

---

### **3. 实现 `Callable` 接口**
通过实现 `Callable` 接口并实现 `call()` 方法来创建线程。`Callable` 可以有返回值，并且可以抛出异常。

#### **示例代码**
```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("线程运行中: " + Thread.currentThread().getName());
        return "线程执行完成";
    }
}

public class CallableExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建 Callable 对象
        MyCallable myCallable = new MyCallable();
        
        // 创建 FutureTask 对象（用于接收返回值）
        FutureTask<String> futureTask = new FutureTask<>(myCallable);
        
        // 创建线程对象
        Thread thread = new Thread(futureTask);
        
        // 启动线程
        thread.start();
        
        // 获取线程返回值
        String result = futureTask.get();
        System.out.println("线程返回值: " + result);
    }
}
```

**输出**：
```
线程运行中: Thread-0
线程返回值: 线程执行完成
```

**特点**：
- 可以返回结果，适合需要获取线程执行结果的场景。
- 可以抛出异常，适合需要处理异常的场景。
- 需要配合 `FutureTask` 或线程池使用。

---

### **总结对比**

| 方式                | 优点                                                                 | 缺点                                                                 |
|---------------------|--------------------------------------------------------------------|--------------------------------------------------------------------|
| 继承 `Thread` 类     | 简单直接，适合简单任务。                                               | 单继承限制，无法继承其他类。                                           |
| 实现 `Runnable` 接口 | 灵活，支持多接口实现，适合共享资源。                                     | 无返回值，无法直接获取线程执行结果。                                     |
| 实现 `Callable` 接口 | 支持返回值和异常处理，适合需要获取结果的场景。                             | 使用稍复杂，需要配合 `FutureTask` 或线程池。                             |

---

### **选择建议**
- 如果任务简单且不需要返回值，可以使用 **`Runnable`**。
- 如果需要返回值或处理异常，可以使用 **`Callable`**。
- 如果任务非常简单且不需要共享资源，可以使用 **`Thread`**。

如果有更多问题，欢迎随时提问！ 😊