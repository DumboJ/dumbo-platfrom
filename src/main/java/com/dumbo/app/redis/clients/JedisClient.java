package com.dumbo.app.redis.clients;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@RestController
@RequestMapping("/jedisSet")
public class JedisClient {
    public void doWithJedisSingleton(){
        JedisPool pool = new JedisPool("192.168.5.101", 63779,);
        try (
                Jedis jedis = pool.getResource()) {
            jedis.set("clientName", "Jedis");
        }
    }
}
