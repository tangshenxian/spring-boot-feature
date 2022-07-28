package com.shenxian.factory;

import com.shenxian.strategy.OrderStrategy;
import com.shenxian.utils.ResultBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: shenxian
 * @date: 2022/7/28 10:38
 */
@Component
public class OrderStrategyFactory implements ApplicationContextAware {

    private static final Map<String, OrderStrategy> MAP = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, OrderStrategy> beans = applicationContext.getBeansOfType(OrderStrategy.class);
        beans.values().forEach(bean -> MAP.put(bean.type(), bean));
    }

    public ResultBean commitResult(String type) {
        OrderStrategy strategy = MAP.get(type);
        if (strategy != null) {
            return strategy.commitStrategy();
        }
        return ResultBean.error().message("参数异常，请检查");
    }

    private static class CreateFactorySingleton {
        private static final OrderStrategyFactory FACTORY = new OrderStrategyFactory();
    }

    public static OrderStrategyFactory instance() {
        return CreateFactorySingleton.FACTORY;
    }
}
