package com.shenxian.strategy;

import com.shenxian.utils.ResultBean;

/**
 * 下单策略接口
 * @author: shenxian
 * @date: 2022/7/28 10:06
 */
public interface OrderStrategy {

    /**
     * 策略标识
     * @return /
     */
    String type();

    /**
     * 提交
     * @return /
     */
    ResultBean commitStrategy();

}
