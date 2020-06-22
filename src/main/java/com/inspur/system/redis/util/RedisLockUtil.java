package com.inspur.system.redis.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Component
public class RedisLockUtil implements Lock {
    private static final String KEY = "LOCK_KEY";
    /**
     * 不存在则设置k - v 值
     */
    private static final String SET_IF_NOT_EXIST = "NX";
    /**
     * 设置毫秒超时
     */
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    @Resource
    private RedisTemplate<String, Object> redisTemplateCustomize;
    private ThreadLocal<String> local = new ThreadLocal<>();

    @Override
    //阻塞式的加锁
    public void lock() {
        //1.尝试加锁
        if (tryLock()) {
            return;
        }
        //2.加锁失败，当前任务休眠一段时间
        try {
            Thread.sleep(10);//性能浪费
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //3.递归调用，再次去抢锁
        lock();
    }


    @Override
    //阻塞式加锁,使用setNx命令返回OK的加锁成功，并生产随机值
    public boolean tryLock() {
        //产生随机值，标识本次锁编号
        String uuid = UUID.randomUUID().toString();
        System.out.println("加锁ID：" + uuid);
        String script = FileUtils.getScript("lock.lua");
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<Boolean>(script, Boolean.class);
        /**
         * key:我们使用key来当锁
         * uuid:唯一标识，这个锁是我加的，属于我
         * 1000：有效时间为 1 秒
         */
        Boolean result = redisTemplateCustomize.execute(redisScript, Arrays.asList(KEY), uuid, 100000);
        //设值成功--抢到了锁,抢锁成功，把锁标识号记录入本线程--- Threadlocal
        System.out.println("加锁结果：" + result.booleanValue());
        if (result.booleanValue()) {
            local.set(uuid);
            return true;
        }
        //key值里面有了，我的uuid未能设入进去，抢锁失败
        return false;
    }

    //正确解锁方式
    public void unlock() {
        //读取lua脚本
        String script = FileUtils.getScript("unlock.lua");
        //获取redis的原始连接
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<Boolean>(script, Boolean.class);
        String uuid = local.get().toString();
        System.out.println("释放锁ID：" + uuid);
        Boolean state = redisTemplateCustomize.execute(redisScript, Arrays.asList(KEY), local.get().toString());
        System.out.println("释放锁" + state.toString());
    }

    //-----------------------------------------------

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit)
            throws InterruptedException {
        return false;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

}
