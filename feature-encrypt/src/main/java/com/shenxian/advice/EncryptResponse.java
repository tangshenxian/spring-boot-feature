package com.shenxian.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenxian.annotation.Encrypt;
import com.shenxian.properties.EncryptProperties;
import com.shenxian.utils.AESUtil;
import com.shenxian.utils.ResultBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author: shenxian
 * @date: 2022/2/28 10:40
 */
@ControllerAdvice
public class EncryptResponse implements ResponseBodyAdvice<ResultBean> {

    private ObjectMapper objectMapper = new ObjectMapper();;

    @Autowired
    private EncryptProperties encryptProperties;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public ResultBean beforeBodyWrite(ResultBean resultBean, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        byte[] keyBytes = encryptProperties.getKey().getBytes();
        try {
            if (StringUtils.isNotBlank(resultBean.getMessage())) {
                resultBean.setMessage(AESUtil.encrypt(resultBean.getMessage().getBytes(), keyBytes));
            }
            if (resultBean.getData() != null) {
                resultBean.setData(AESUtil.encrypt(objectMapper.writeValueAsBytes(resultBean.getData()), keyBytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultBean;
    }
}
