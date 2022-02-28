package com.shenxian.utils;

import lombok.Data;

/**
 * @author: shenxian
 * @date: 2022/2/28 10:33
 */
@Data
public class ResultBean {

    private Integer code;
    private String message;
    private Object data;

    private ResultBean() {}

    public static ResultBean success() {
        ResultBean result = new ResultBean();
        result.setCode(200);
        result.setMessage("成功");
        return result;
    }

    public static ResultBean error() {
        ResultBean result = new ResultBean();
        result.setCode(500);
        result.setMessage("失败");
        return result;
    }

    public ResultBean code(Integer code) {
        this.code = code;
        return this;
    }

    public ResultBean message(String message) {
        this.message = message;
        return this;
    }

    public ResultBean data(Object data) {
        this.data = data;
        return this;
    }
}
