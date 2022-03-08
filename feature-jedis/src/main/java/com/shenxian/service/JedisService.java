package com.shenxian.service;

import java.util.List;

/**
 * @author: shenxian
 * @date: 2022/3/8 9:52
 */
public interface JedisService {
    /**
     * 模拟抢单
     * @return
     */
    List<String> grabOrder();
}
