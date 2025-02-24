package xCloud.redis.lock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author Andy Fan
 * @Date 2025/2/24 14:09
 * @ClassName OptimizedRedissonLock 基于Redisson的分布式锁实现，重点优化了公平锁和超时策略
 */
public class OptimizedRedissonLock {

    private final RedissonClient redissonClient;


    //config redisson client
    public OptimizedRedissonLock() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setConnectionPoolSize(100)
                .setTimeout(3000);
        redissonClient = Redisson.create(config);
    }

    //use fair lock do business logic
    public boolean doWithFairLock(String lockName, long waitTime, long leaseTime) {
        //get fair lock
        RLock fairLock = redissonClient.getFairLock(lockName);
        boolean locked = false;
        try {
            locked = fairLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if (locked) {
                System.out.println("thread " + Thread.currentThread().getName() + " get lock successfully");
                //do something
                Thread.sleep(3000);
                System.out.println("thread " + Thread.currentThread().getName() + " do something");
                System.out.println("well done!");
                return true;
            } else {
                System.out.println("thread " + Thread.currentThread().getName() + " get lock failed");
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted while obtaining lock");
            return false;
        } finally {
            if (locked && fairLock.isHeldByCurrentThread()) {
                fairLock.unlock();
                System.out.println("thread " + Thread.currentThread().getName() + " release fair lock");
            }
        }

    }

    //    close redisson client
    public void shutdown() {
        redissonClient.shutdown();
    }

    public static void main(String[] args) {

        OptimizedRedissonLock lock = new OptimizedRedissonLock();
        Runnable task = () -> lock.doWithFairLock("testLock", 5, -1);
        //-1 means enable watchdog
        Thread thread1 = new Thread(task, "thread 1");
        Thread thread2 = new Thread(task, "thread 2---");
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.shutdown();

    }
}
