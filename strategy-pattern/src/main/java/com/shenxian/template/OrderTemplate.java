package com.shenxian.template;

import com.shenxian.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;

/**
 * 下单模板
 * @author: shenxian
 * @date: 2022/7/28 10:13
 */
@Slf4j
public abstract class OrderTemplate {

    public ResultBean commitTemplate() {
        // 校验
        this.check();
        // 提交
        return this.commit();
    }

    protected void check() {
        // 身份证校验
        this.certCheck();

        // 风控校验
        if (this.isRiskCheck()) {
            this.riskCheck();
        }

        // 一证五户校验
        this.fiveCheck();
    }

    protected void certCheck() {
        log.info("身份证校验。。。");
    }

    protected void riskCheck() {
        log.info("风控校验。。。");
    }

    protected void fiveCheck() {
        log.info("一证五户校验。。。");
    }

    protected boolean isRiskCheck() {
        return true;
    }

    protected abstract ResultBean commit();

}
