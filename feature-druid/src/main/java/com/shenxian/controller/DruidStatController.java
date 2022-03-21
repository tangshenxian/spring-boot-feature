package com.shenxian.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: shenxian
 * @date: 2022/3/21 16:19
 */
@RestController
@RequestMapping(value = "/druid-stat")
public class DruidStatController {

    /**
     * 获取数据源的监控数据
     * @return
     */
    @GetMapping("/list")
    public Object druidStat() {
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }

}
