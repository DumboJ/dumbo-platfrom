package com.dumbo.app.redis;

import redis.clients.jedis.JedisPool;

/***
 * redis锁操作
 * */
public interface LockService<T> {
     abstract boolean tryLock(T key,String reqValue);

    abstract boolean tryLock(T key);

     boolean unlock(T key,String value);

     Long delLock(T key);

     String getLock(T key);

     void setExpire(T key);

    public Long getExpire(T key);

    public String putLock(T key,String value);

    public void setClient(JedisPool jedisPool);

//    public void setConfig(RouterConfig config);

}
