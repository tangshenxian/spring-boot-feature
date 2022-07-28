package com.shenxian.service;

import com.shenxian.common.OrderType;
import com.shenxian.strategy.OrderStrategy;
import com.shenxian.template.OrderTemplate;
import com.shenxian.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 宽带下单
 * @author: shenxian
 * @date: 2022/7/28 10:12
 */
@Slf4j
@Service
public class BroadbandOrderStrategyService extends OrderTemplate implements OrderStrategy {
    @Override
    public String type() {
        return OrderType.ORDER_TYPE_BROADBAND;
    }

    @Override
    public ResultBean commitStrategy() {
        return super.commitTemplate();
    }

    @Override
    protected ResultBean commit() {
        log.info("宽带下单提交");
        return ResultBean.success().message("宽带下单成功");
    }
}
