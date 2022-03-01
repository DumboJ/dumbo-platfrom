package com.dumbo.app.redis.clients;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;

public class RedisUriBuilder {
    RedisURI redisUri = RedisURI.builder().withHost("192.168.5.101").withPort(6379).withSsl(true).withPassword("dumboj123").build();
    RedisClient client = RedisClient.create(redisUri);
}
