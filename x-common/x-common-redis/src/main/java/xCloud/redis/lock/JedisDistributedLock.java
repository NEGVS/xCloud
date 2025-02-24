package xCloud.redis.lock;

import jakarta.annotation.Resource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.UUID;

/**
 * @Description
 * @Author Andy Fan
 * @Date 2025/2/24 09:44
 * @ClassName RedisDistributedLock use jedis
 */
public class JedisDistributedLock {
    private static final String LOCK_KEY_PREFIX = "lock:";
    private static final long DEFAULT_EXPIRE_TIME = 30000; // 默认锁过期时间，单位毫秒
    private static final String UNLOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "return redis.call('del', KEYS[1]) else " +
            "return 0 end";


    @Resource
    private Jedis jedis;

    public JedisDistributedLock(Jedis jedis) {
        this.jedis = jedis;
    }


    //    get lock
    public String acquireLock(String keyName, long expireTime) {

        String lockKey = LOCK_KEY_PREFIX + keyName;
        String lockValue = UUID.randomUUID().toString();
        String result = jedis.set(lockKey, lockValue, SetParams.setParams().nx().px(expireTime));
        if ("OK".equals(result)) {
            return lockValue;
        }
        return null;
    }

    //    releaseLock
    public boolean releaseLock(String keyName, String lockValue) {
        String lockKey = LOCK_KEY_PREFIX + keyName;
        //使用lua脚本确保原子性释放
        Object result = jedis.eval(UNLOCK_SCRIPT, Collections.singletonList(lockKey), Collections.singletonList(lockValue));
        return Long.valueOf(1).equals(result);
    }

    //    example
    public static void main(String[] args) {
        Jedis jedis1 = new Jedis("localhost", 6379);
        JedisDistributedLock lock1 = new JedisDistributedLock(jedis1);
        String lockName = "testLock";
        String lockValue = lock1.acquireLock(lockName, DEFAULT_EXPIRE_TIME);

        if (lockValue != null) {
            try {
                System.out.println("Lock acquired with value: " + lockValue);
                Thread.sleep(2000);
                System.out.println("start data...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                boolean b = lock1.releaseLock(lockName, lockValue);
                System.out.println("release lock:" + (b ? "success" : "fail"));
            }
        } else {
            System.out.println("Lock failed to acquire.");
        }
        jedis1.close();
    }
}
