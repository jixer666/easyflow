package com.abc.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentConfig {
    public final static String DEV = "dev";
    public final static String PROD = "prod";

    @Value("${spring.profiles.active}")
    private String env;

    public Boolean isDevEnv() {
        return DEV.equals(env);
    }

    public Boolean isProdEnv() {
        return PROD.equals(env);
    }
}