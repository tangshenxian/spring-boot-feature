package com.shenxian.controller;

import com.shenxian.annotation.Decrypt;
import com.shenxian.annotation.Encrypt;
import com.shenxian.pojo.User;
import com.shenxian.utils.ResultBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: shenxian
 * @date: 2022/2/28 10:29
 */
@RestController
public class EncryptController {

    /**
     * 加密
     * @return
     */
    @Encrypt
    @GetMapping("/encrypt")
    public ResultBean encrypt() {
        User user = new User();
        user.setName("测试用户");
        user.setAge(18);
        user.setDescription("这是测试用户！！！");
        return ResultBean.success().data(user);
    }

    /**
     * 解密
     * @param user
     * @return
     */
    @PostMapping("/decrypt")
    public ResultBean decrypt(@RequestBody @Decrypt User user) {
        return ResultBean.success().data(user);
    }

}
