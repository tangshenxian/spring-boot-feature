package com.shenxian.controller;

import com.shenxian.service.JedisService;
import com.shenxian.utils.JedisUtil;
import com.shenxian.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: shenxian
 * @date: 2022/3/8 9:19
 */
@Slf4j
@RestController
public class JedisLockController {

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private JedisService jedisService;

    /**
     * 抢单入口
     * @return
     */
    @GetMapping("/grabOrder")
    public ResultBean grabOrder() {
        return ResultBean.success().data(jedisService.grabOrder());
    }

    /**
     * jedis.setNx()测试
     * @param key
     * @param value
     * @return
     */
    @GetMapping("/setNx/{key}/{value}")
    public ResultBean setNx(@PathVariable("key") String key, @PathVariable("value") String value) {
        return ResultBean.success().data(jedisUtil.setNx(key, value));
    }

    /**
     * jedis.delNx()测试
     * @param key
     * @param value
     * @return
     */
    @GetMapping("/delNx/{key}/{value}")
    public ResultBean delNx(@PathVariable("key") String key, @PathVariable("value") String value) {
        return ResultBean.success().data(jedisUtil.delNx(key, value));
    }

}
