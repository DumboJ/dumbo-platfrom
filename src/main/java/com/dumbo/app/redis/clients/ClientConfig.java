package com.dumbo.app.redis.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
/**
 * redis客户端操作类：Lettuce
 *                  Jedis
 *                  bean初始化
 * */
@Configuration
public class ClientConfig {
    /**
     * 单例Lettuce客户端链接
     * */
    @Bean
    public LettuceConnectionFactory redisConntectionFactory(){
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("192.168.5.101", 6379));
    }
}
