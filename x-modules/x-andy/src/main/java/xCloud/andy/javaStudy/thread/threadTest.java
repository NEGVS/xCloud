package xCloud.andy.javaStudy.thread;

import io.micrometer.observation.annotation.Observed;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *@Description
 *@Author Andy Fan
 *@Date 2025/2/12 14:43
 *@ClassName threadTest
 */
public class threadTest
{

   public static void main( String[] args )
   {
      //1- extents thread ,create thread object
      MyThread thread = new MyThread();
      thread.start();
      //2- implements Runnable interface and implement run() method to create thread.
      MyRunnable runnable = new MyRunnable();
      Thread thread2 = new Thread( runnable );
      thread2.start();
      //3-实现 Callable 接口并实现 call() 方法来创建线程。Callable 可以有返回值，并且可以抛出异常。
      //create callable object
      MyCallable callable = new MyCallable();
      //create futureTask object ,to result callable value
      FutureTask< String > futureTask = new FutureTask<>( callable );

      Thread thread3 = new Thread( futureTask );
      thread3.start();
      try
      {
         //get()方法会阻塞当前线程，直到FutureTask中的call()方法执行完毕，并返回结果。
         System.out.println( "等待中.....................get()方法会阻塞当前线程，直到FutureTask中的call()方法执行完毕，并返回结果。" );
         //get()方法会阻塞当前线程，直到FutureTask中的call()方法执行完毕，并返回结果。
         String result = futureTask.get();
         System.out.println( "等待结束....................." );

         System.out.println( "Callable result: " );
         System.out.println( result );
      }
      catch ( ExecutionException | InterruptedException e )
      {
         throw new RuntimeException( e );
      }
   }
}

/**
 * 3-实现 Callable 接口并实现 call() 方法来创建线程。Callable 可以有返回值，并且可以抛出异常。
 * 可以返回结果，适合需要获取线程执行结果的场景。
 * 可以抛出异常，适合需要处理异常的场景。
 * 需要配合 FutureTask 或线程池使用。
 */
class MyCallable implements Callable< String >
{
   @Override
   public String call() throws Exception
   {
      System.out.println( "Callable 线程开始睡觉======" );
      Thread.sleep( 6000 );
      System.out.println( "Callable 线程开始醒来了-----" );
      System.out.println( "Callable 111线程运行中: " + Thread.currentThread().getName() );
      return "Callable 222运行完成: " + Thread.currentThread().getName();
   }
}

/**
 *2-实现 Runnable 接口并实现 run() 方法来创建线程。 更灵活，因为 Java 支持多接口实现。
 *  适合需要共享资源的场景（多个线程可以共享同一个 Runnable 对象）
 */
class MyRunnable implements Runnable
{
   @Override
   public void run()
   {
      System.out.println( "Runnable 线程运行中: " + Thread.currentThread().getName() );
   }
}

/**
 *1-继承 Thread 类并重写 run() 方法来创建线程。
 * 简单直接，适合简单的线程任务。
 * 由于 Java 是单继承，继承 Thread 类后无法再继承其他类
 */
class MyThread extends Thread
{
   @Override
   public void run()
   {
      System.out.println( "Thread 线程运行中: " + Thread.currentThread().getName() );
   }
}
