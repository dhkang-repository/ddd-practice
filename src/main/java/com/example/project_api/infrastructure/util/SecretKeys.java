package com.example.project_api.infrastructure.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;


public class SecretKeys {
    public static final Logger logger = LogManager.getLogger(SecretKeys.class);

    private Set<String> appkeys;

    public SecretKeys(Set<String> appkeys) {
        this.appkeys = appkeys;
    }

    public boolean isValid(String appkey) {
        return appkeys.contains(appkey);
    }
}
