/*
package com.autonavi.traffic.autobot.router.service.impl;


import com.dumbo.app.redis.LockService;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;
import java.util.Arrays;
import java.util.UUID;

*/
/**
 * @author dongrui.zdr
 * @ClassName LockServiceImpl
 * @Description 锁操作实现类
 * @date 2019-06-04 11:03
 *//*

public class LockServiceImpl<T, RouterConfig> implements LockService<T> {
    private static final Logger logger = LoggerFactory.getLogger(LockServiceImpl.class);

    */
/**
     * ?¨Òªì©l¤Æ«È?ºÝ
     *//*

    private static class SingletonHolder {
        private static final LockService INSTANCE = new LockServiceImpl();
    }

    private LockServiceImpl() {
    }

    public static final synchronized LockService getInstance() {
        return LockServiceImpl.SingletonHolder.INSTANCE;
    }

    */
/**
     * Jedis?
     *//*

    private JedisPool jedisPool;

    */
/**
     * °t¸m?
     *//*

    private RouterConfig config;

    */
/**
     * ???¨ú?¡Aget©Îput¥¢?³£¤À??¦æ­«?¡A¤ä«ù­«¤J
     *
     * @param key
     * @return
     *//*

    @Override
    public boolean tryLock(T key, String reqValue) {
        try {
            // ¤ä«ù­«?
            String lockValue = getLock(key);
            if (lockValue != null) {
//                if (reqValue != null && CommonUtils.equalByLockV(lockValue, reqValue)) {
                if (reqValue != null && lockValue.equals(reqValue)) {
                    logger.info("tryLock true,reenterable lock,  key={}, value={}", key, new String(reqValue));
                    return true;
                }
                logger.error("tryLock fail,  key={}, value={}", key, lockValue);
                return false;
            }
            String value = reqValue;
            if (value == null) {
//                value = CommonUtils.genByteLockV();
                value = UUID.randomUUID().toString();
            }
            String putResult = putLock(key, value);
            if (putResult != null) {
                logger.info("tryLock success, key={}, value={}", key, value);
                return true;
            } else {
                logger.error("tryLock fail,  key={},  value={}, ResultCode={}", key, value, putResult);
                return false;
            }
        } catch (Exception e) {
            logger.error("tryLock error", e);
        }
        return false;
    }

    */
/**
     * ¤£¥i­«¤J
     *
     * @param key
     * @return
     *//*

    @Override
    public boolean tryLock(T key) {
        return tryLock(key, null);
    }

    */
/**
     * §P?¬O§_¬O?«e?µ{«ù¦³ªº?¡A?©ñ?
     *//*

    @Override
    public boolean unlock(T key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = null;
        int retry = 0;
        byte[] keyByte = toKeyByte(key);
        byte[] valueByte = value.getBytes();
//        while (retry++ < config.getRetry() && result == null) {
        while (retry++ < 3 && result == null) {
            try {
                byte[] bytes = jedis.get(keyByte);
                boolean flag = Arrays.equals(bytes, valueByte);
                if (flag) {
                    logger.info("unlock success, key={}, value={}", key, value);
                    jedis.del(keyByte);
                    return true;
                } else {
                    logger.error("unlock failed, key={}, value={}", key, value);
                    return false;
                }
            } catch (Exception e) {
                logger.warn("key {} value {} unlock warn timeout", key, value, e);
            } catch (Throwable e) {
                logger.error("key {} value {} unlock error", key, value, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
        logger.error("unlock failed, key={}, value={}", key, value);
        return false;
    }



    */
/**
     * @Description: delLock  ?°£?
     * @param: [lockKey]
     * @return: java.lang.Long
     * @Author: dongrui.zdr
     * @Date: 2019-06-04
     *//*

    @Override
    public Long delLock(T key) {
        Jedis jedis = jedisPool.getResource();
        Long lockValue = null;
        int retry = 0;
        byte[] keyByte = toKeyByte(key);
//        while (retry++ < config.getRetry() && lockValue == null) {
        while (retry++ < 3 && lockValue == null) {
            try {
                lockValue = jedis.del(keyByte);
            } catch (Throwable e) {
                logger.error("key {} delLock error", key, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
        return lockValue;
    }

    */
/**
     * @Description: getLock ?¨ú?­È
     * @param: [dataVersion]
     * @return: java.lang.String
     * @Author: dongrui.zdr
     * @Date: 2018/7/25
     *//*

    @Override
    public String getLock(T key) {
        Jedis jedis = jedisPool.getResource();
        byte[] lockValue = null;
        int retryGet = 0;
        byte[] keyByte = toKeyByte(key);
//        while (retryGet++ < config.getRetry() && lockValue == null) {
        while (retryGet++ < 3 && lockValue == null) {
            try {
                lockValue = jedis.get(keyByte);
            } catch (Throwable e) {
                logger.error("key {} getLock error", key, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
        return lockValue == null ? null : new String(lockValue);
    }

    */
/**
     * @Description: setExpire ?¸m?¶W???
     * @param: [dataVersion]
     * @return: void
     * @Author: dongrui.zdr
     * @Date: 2018/7/25
     *//*

    @Override
    public void setExpire(T key) {
        Jedis jedis = jedisPool.getResource();
        Long result = null;
        int retry = 0;
        byte[] keyByte = toKeyByte(key);
//        while (retry++ < config.getRetry() && result == null) {
        while (retry++ < 3 && result == null) {
            try {
//                result = jedis.expire(keyByte, config.getTimeout());
                result = jedis.expire(keyByte, 60);
            } catch (Throwable e) {
                logger.error("key {} setExpire error", key, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }


    */
/**
     * @Description: getExpire ?¨ú?¶W???
     * @param: [dataVersion]
     * @return: java.lang.Long
     * @Author: dongrui.zdr
     * @Date: 2018/7/25
     *//*

    @Override
    public Long getExpire(T key) {
        Jedis jedis = jedisPool.getResource();
        Long time = null;
        int retry = 0;
        byte[] keyByte = toKeyByte(key);
//        while (retry++ < config.getRetry() && time == null) {
        while (retry++ < 3 && time == null) {
            try {
                time = jedis.ttl(keyByte);
            } catch (Throwable e) {
                logger.error("key {} getExpire error", key, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
        return time;
    }

    */
/**
     * ¤èªk¥Î³~: ©ñ¤J?<br>
     * ??¨B?: <br>
     *
     * @param key   ?key
     * @param value ?­È
     * @return
     * @throws Exception
     *//*

    @Override
    public  putLock(T key, String value) {
        Jedis jedis = jedisPool.getResource();
        // redis?¦s
        String putResult = null;
        int retryPut = 0;
        SetParams sp = new SetParams();
        sp.nx();
//        sp.ex(config.getTimeout());
        sp.ex(15L);
        byte[] keyByte = toKeyByte(key);
        byte[] valueByte = value.getBytes();
        // ¤ä«ù­«?
        while (retryPut++ < 3 && putResult == null) {
//        while (retryPut++ < config.getRetry() && putResult == null) {
//        while (retryPut++ < config.getRetry() && putResult == null) {
        while (retryPut++ < 3 && putResult == null) {
            try {
                putResult = jedis.set(keyByte, valueByte, sp);
            } catch (Throwable e) {
                logger.error("key {} value {} putLock error", key, value, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
        return putResult;
    }

    @Override
    public void setClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public void setConfig(RouterConfig config) {
        this.config = config;
    }

    private byte[] toKeyByte(T key) {
//        return FastJsonUtils.toJsonByte(key);
        return "key.toString().getBytes()".split(".");
    }*/
