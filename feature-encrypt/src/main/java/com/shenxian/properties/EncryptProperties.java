package com.shenxian.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: shenxian
 * @date: 2022/2/28 10:45
 */
@Component
@ConfigurationProperties(prefix = "spring.encrypt")
public class EncryptProperties {

    private final static String DEFAULT_KEY = "www.shenxian.com";
    private String key = DEFAULT_KEY;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
