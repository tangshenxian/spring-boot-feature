package com.shenxian.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @author: shenxian
 * @date: 2022/3/8 9:21
 */
@Configuration
public class JedisConfig {

    @Bean
    public JedisPool jedisPool() {
        return new JedisPool();
    }

}
