package com.abc.flowabled.strategy.impl;

import com.abc.flowabled.strategy.FlowAllEventListenerStrategy;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProcessStartedEventListener implements FlowAllEventListenerStrategy, InitializingBean {

    @Override
    public void handle(FlowableEvent event) {
        log.info("流程实例开始：{}", event.getType().name());

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        afterPropertiesSet(FlowableEngineEventType.PROCESS_STARTED.toString());

    }

}
