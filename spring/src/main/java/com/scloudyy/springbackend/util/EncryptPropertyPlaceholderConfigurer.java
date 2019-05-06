package com.scloudyy.springbackend.util;


import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private Set<String> encryptPropNames = new HashSet<>(Arrays.asList("jdbc.username", "jdbc.password"));

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName)) {
            String decryptValue = DESUtil.getDecryptString(propertyValue);
            return decryptValue;
        } else {
            return propertyValue;
        }
    }

    private boolean isEncryptProp(String propertyName) {
        // 若等于需要加密的field，则进行加密
        return encryptPropNames.contains(propertyName);
    }
}
