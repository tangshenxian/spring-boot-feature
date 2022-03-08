package com.shenxian.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

/**
 * @author: shenxian
 * @date: 2022/3/8 9:24
 */
@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    public boolean setNx(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            SetParams setParams = new SetParams();
            // nx()如果存在就set不成功 px设置过期时间
            setParams.nx().px(1000 * 60);
            return "ok".equalsIgnoreCase(jedis.set(key, value, setParams));
        } catch (Exception ignored) {

        }
        return false;
    }

    public int delNx(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            String builder = "if redis.call('get','" + key + "')" + "=='" + value + "'" +
                    " then " +
                    "    return redis.call('del','" + key + "')" +
                    " else " +
                    "    return 0" +
                    " end";
            return Integer.parseInt(jedis.eval(builder).toString());
        } catch (Exception ignore) {

        }
        return 0;
    }

}
