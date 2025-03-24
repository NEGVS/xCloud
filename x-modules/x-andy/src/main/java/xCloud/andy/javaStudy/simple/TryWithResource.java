package xCloud.andy.javaStudy.simple;

/**
 * @Description
 * @Author Andy Fan
 * @Date 2025/3/23
 * @ClassName TryWithResource
 * 在 Java 中，try-with-resources 是一种用于管理资源的语法结构，特别适用于需要确保资源（如文件流、网络连接、数据库连接等）在使用完毕后被正确关闭的场景。它是在 Java 7 中引入的，目的是简化资源管理的代码并减少资源泄漏的可能性。
 * 基本概念
 * try-with-resources 的核心是利用实现了 AutoCloseable 接口（或其子接口 Closeable）的类。这些类通常在资源使用完成后需要调用 close() 方法来释放资源。try-with-resources 确保在 try 块执行完毕（无论是正常结束还是抛出异常）后，自动调用这些资源的 close() 方法。
 * 语法
 * 基本语法如下：
 *
 * try (资源声明) {
 *     // 使用资源的代码
 * } catch (异常类型 e) {
 *     // 处理异常
 * }
 *
 * 资源声明：在 try 后的括号中声明并初始化需要管理的资源，多个资源用分号 ; 分隔。
 *
 * 资源必须实现 AutoCloseable 或 Closeable 接口。
 *
 * 资源会在 try 块结束后自动关闭，无需显式调用 close()。
 *
 */
public class TryWithResource {
    public static void main(String[] args) {
        try (MyResource resource = new MyResource()) {
            resource.doSomething();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Resource closed.");
    }
}


class MyResource implements AutoCloseable {
    public void doSomething() {
        System.out.println("Doing something...");
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closing resource...");
    }
}
