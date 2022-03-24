package com.dumbo.app.redis.clients;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@RestController
@RequestMapping(value = "/jedisSet")
public class JedisClient {
    @RequestMapping(value = "authSet")
    public String doWithJedisSingleton(){
        Jedis jedis = null;
        try {
            jedis = new Jedis("192.168.5.101", 6379);
                jedis.auth("dumboj123");
            jedis.set("clientName", "Jedis");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return "success";
    }
}
