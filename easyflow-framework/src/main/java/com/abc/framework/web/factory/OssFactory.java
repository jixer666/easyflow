package com.abc.framework.web.factory;

import com.abc.framework.web.domain.enums.OssEnum;
import com.abc.framework.web.strategy.oss.OssStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OssFactory {

    @Autowired
    private ApplicationContext applicationContext;

    private static Map<Integer, OssStrategy> ossStrategyMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        applicationContext.getBeansOfType(OssStrategy.class).forEach((k,v)->{
            ossStrategyMap.put(OssEnum.getTypeByOssKey(k),v);
        });
    }

    public static OssStrategy getOssStrategy(Integer ossType) {
        return ossStrategyMap.get(ossType);
    }

}
