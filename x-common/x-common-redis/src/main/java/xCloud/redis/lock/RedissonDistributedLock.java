package xCloud.redis.lock;

import jakarta.annotation.Resource;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author Andy Fan
 * @Date 2025/2/24 13:38
 * @ClassName RedissonDistributedLock 在分布式系统中，Redisson是一个强大的Java客户端库，基于Redis提供高级功能，包括高效的分布式锁实现。相比手动使用Jedis或RedisTemplate实现分布式锁，Redisson内置了许多优化特性，比如自动续期、公平锁和高并发支持。
 * 下面我将展示如何用Redisson实现一个高效的分布式锁，并分析其实现原理和优势。
 */
public class RedissonDistributedLock {

    @Resource
    private RedissonClient redissonClient;

    public RedissonDistributedLock() {
        //config redisson client
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setConnectionPoolSize(100)
                .setConnectionMinimumIdleSize(50)
                .setTimeout(3000);
        this.redissonClient = Redisson.create(config);
    }

    //    execute with lock business logic
    public void doWithLock(String lockName, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(lockName);
        try {
//            get lock
            boolean locked = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if (locked) {
                System.out.println("thread " + Thread.currentThread().getName() + " get lock successfully");
                // do something
                Thread.sleep(2000);
                System.out.println("thread " + Thread.currentThread().getName() + " do something");
                System.out.println("well done!");
            } else {
                System.out.println("thread " + Thread.currentThread().getName() + " get lock failed");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted while obtaining lock");
        } finally {
            // If the lock is held by the current thread, release it
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("thread " + Thread.currentThread().getName() + " release lock");
            }

        }
    }

    //    close redisson client
    public void shutdown() {
        redissonClient.shutdown();
    }

    public static void main(String[] args) {
        RedissonDistributedLock lockDemo = new RedissonDistributedLock();
//        mul thread test
        Runnable task = () -> lockDemo.doWithLock("testLock", 5, 10);
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
        lockDemo.shutdown();
    }
}
