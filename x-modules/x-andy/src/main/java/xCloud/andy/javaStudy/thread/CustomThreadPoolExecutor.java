package xCloud.andy.javaStudy.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author Andy Fan
 * @Date 2025/2/12 16:26
 * @ClassName CustomerThreadPool
 */
public class CustomThreadPoolExecutor extends ThreadPoolExecutor
{

   public CustomThreadPoolExecutor( int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue< Runnable > workQueue )
   {
      super( corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue );
   }

   @Override
   protected void afterExecute( Runnable r, Throwable t )
   {
      super.afterExecute( r, t );
      if ( t != null )
      {
         System.out.println( "thread terminated with exception :" + t.getMessage() );
      }
   }

   public static void main( String[] args )
   {
      CustomThreadPoolExecutor executor2 = new CustomThreadPoolExecutor( 5, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>( 10 ) );

      for ( int i = 0; i < 10; i++ )
      {
         executor2.execute( new TaskWithException() );
         System.out.println( i );
      }
      executor2.shutdown();

      //初步设置
      int corePoolSize = Runtime.getRuntime().availableProcessors();
      int maxPoolSize = corePoolSize * 2;
      AtomicInteger queueCapacity = new AtomicInteger( corePoolSize * 10 );  //初始设置
      //动态调整线程池参数
      ThreadPoolExecutor executor = new ThreadPoolExecutor( corePoolSize, maxPoolSize, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>( queueCapacity.get() ), new ThreadPoolExecutor.AbortPolicy() );
      //监控队列长度
      ScheduledExecutorService monitor = Executors.newScheduledThreadPool( 1 );

      monitor.scheduleAtFixedRate( () -> {
         int currentQueueSize = executor.getQueue().size();
         System.out.println( "Current queue size: " + currentQueueSize );
         //动态调整逻辑（示例）
         if ( currentQueueSize > corePoolSize * 8 )
         {
            queueCapacity.addAndGet( corePoolSize );  //队列容量不足，增加队列大小
            System.out.println( "Increased queue capacity to: " + queueCapacity );
         }
      }, 0, 10, TimeUnit.SECONDS );

   }
}
