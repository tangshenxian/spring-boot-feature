package com.shenxian.controller;

import com.shenxian.common.OrderReq;
import com.shenxian.factory.OrderStrategyFactory;
import com.shenxian.utils.ResultBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: shenxian
 * @date: 2022/7/28 10:46
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping("/commit")
    public ResultBean commit(@RequestBody OrderReq req) {
        return OrderStrategyFactory.instance().commitResult(req.getType());
    }

}
