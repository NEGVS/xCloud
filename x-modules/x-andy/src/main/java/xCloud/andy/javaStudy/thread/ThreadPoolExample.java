package xCloud.andy.javaStudy.thread;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *@Description
 *@Author Andy Fan
 *@Date 2025/2/12 15:27
 *@ClassName ThreadPoolExample
 *https://blog.csdn.net/Rookie_CEO/article/details/140742693
 *
 */
public class ThreadPoolExample
{
   public static void main( String[] args ) throws IOException
   {

      //      ExecutorService executorService = new ThreadPoolExecutor(  2,2,2, TimeUnit.SECONDS, BlockingDeque<String> , new ThreadPoolExecutor.AbortPolicy());
      //      这 7 个参数分别是：
      //1. corePoolSize：核心线程数。
      //2. maximumPoolSize：最大线程数。
      //3. keepAliveTime：空闲线程存活时间。
      //4. TimeUnit：时间单位。
      //5. BlockingQueue：线程池任务队列。
      //6. ThreadFactory：创建线程的工厂。
      //7. RejectedExecutionHandler：拒绝策略。------| |-----| |-----| |-----| |---
      // create fixed thread pool
      System.out.print( "CPU核数：" );
      System.out.println( Runtime.getRuntime().availableProcessors() );

      System.out.print( "总内存大小:" );
      System.out.println( Runtime.getRuntime().maxMemory() / 1024 / 1024 );

      System.out.print( "已经获取的总内存大小:" );
      System.out.println( Runtime.getRuntime().totalMemory() / 1024 / 1024 );

      System.out.print( "获取剩余空间的大小:" );
      System.out.println( Runtime.getRuntime().freeMemory() / 1024 / 1024 );
      System.out.println( "运行cmd命令:" );
      Runtime.getRuntime().exec( "shutdown" );
      System.out.println( "停止虚拟机:" );
      Runtime.getRuntime().exit( 0 );

      ExecutorService executorService = Executors.newFixedThreadPool( 2 );
      //submit task
      executorService.submit( () -> {
         System.out.println( "Task 1 executed by " + Thread.currentThread().getName() );
      } );
      executorService.submit( () -> {
         System.out.println( "Task 2 executed by " + Thread.currentThread().getName() );
      } );
      executorService.submit( () -> {
         System.out.println( "Task 3 executed by " + Thread.currentThread().getName() );
      } );
      //close thread pool
      executorService.shutdown();

   }
}
