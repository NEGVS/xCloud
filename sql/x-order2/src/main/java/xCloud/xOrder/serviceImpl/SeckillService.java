package xCloud.xOrder.serviceImpl;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 *@Description x
 *@Author Andy Fan
 *@Date 2024/12/19 19:22
 *@ClassName SeckillService
 */

@Service
public class SeckillService
{
   @Resource
   private RedisTemplate< String, Object > redisTemplate;

}
