package com.abc.framework.web.factory;

import com.abc.framework.web.domain.enums.LoginTypeEnum;
import com.abc.framework.web.strategy.login.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginFactory {

    @Autowired
    private ApplicationContext applicationContext;

    private static Map<Integer, LoginStrategy> loginStrategyMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        applicationContext.getBeansOfType(LoginStrategy.class).forEach((k,v)->{
            loginStrategyMap.put(LoginTypeEnum.getTypeByLoginKey(k),v);
        });
    }

    public static LoginStrategy getLoginStrategy(Integer loginType) {
        return loginStrategyMap.get(loginType);
    }

}
