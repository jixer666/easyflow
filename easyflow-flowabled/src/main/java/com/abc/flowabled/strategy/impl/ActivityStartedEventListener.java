package com.abc.flowabled.strategy.impl;

import com.abc.flowabled.strategy.FlowAllEventListenerStrategy;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActivityStartedEventListener implements FlowAllEventListenerStrategy, InitializingBean {

    @Override
    public void handle(FlowableEvent flowableEvent) {
        log.info("结点开始：{}", flowableEvent.getType().name());
    }

    @Override
    public void afterPropertiesSet() {
        afterPropertiesSet(FlowableEngineEventType.ACTIVITY_STARTED.toString());
    }
}
