package com.shenxian.service.Impl;

import com.shenxian.service.JedisService;
import com.shenxian.utils.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author: shenxian
 * @date: 2022/3/8 9:52
 */
@Slf4j
@Service
public class JedisServiceImpl implements JedisService {

    /**
     * 设置商品总库存
     */
    private long total = 10;

    /**
     * 设置商品key
     */
    private static final String PRODUCT_KEY = "UNIQUE_PRODUCT_MARKING_";

    /**
     * 设置锁的超时时间30秒
     */
    private static final int TIME_OUT = 30 * 1000;

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public List<String> grabOrder() {
        // 抢到商品的用户
        List<String> successUsers = new ArrayList<>();
        // 构造用户
        List<String> users = new ArrayList<>();
        IntStream.range(0, 100000).forEach(o -> users.add("神仙" + o + "号"));
        // 并行抢单
        users.parallelStream().forEach(user -> {
            String result = grabOperation(user);
            if (StringUtils.isNotBlank(result)) {
                successUsers.add(result);
            }
        });
        return successUsers;
    }

    /**
     * 模拟抢单动作
     *
     * @param user
     * @return
     */
    private String grabOperation(String user) {
        // 抢单开始时间
        long startTime = System.currentTimeMillis();

        // 未抢到的情况下，在超时时间内持续获取锁
        while ((startTime + TIME_OUT) >= System.currentTimeMillis()) {
            // 查看库存
            if (total <= 0) {
                break;
            }
            // 用户拿到锁
            if (jedisUtil.setNx(PRODUCT_KEY, user)) {
                try {
                    log.info("用户{}拿到锁", user);
                    if (total <= 0) {
                        break;
                    }
                    // 模拟抢单操作耗时3秒
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 库存-1
                    total -= 1;
                    log.info("用户{}抢单成功， 库存剩余{}", user, total);
                    return user + "抢单成功， 库存剩余" + total;
                } finally {
                    // 抢单成功后释放锁
                    log.info("用户{}抢单成功，释放锁", user);
                    jedisUtil.delNx(PRODUCT_KEY, user);
                }
            }
        }
        return "";
    }

}
