package com.abc.flowabled.strategy.impl;

import com.abc.flowabled.strategy.FlowAllEventListenerStrategy;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.event.impl.FlowableActivityEventImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActivityStartedEventListener implements FlowAllEventListenerStrategy, InitializingBean {

    @Override
    public void handle(FlowableEvent flowableEvent) {
        FlowableActivityEventImpl flowableActivityEvent = (FlowableActivityEventImpl) flowableEvent;
        String activityId = flowableActivityEvent.getActivityId();
        String activityName = flowableActivityEvent.getActivityName();
        DelegateExecution execution = flowableActivityEvent.getExecution();
        String tenantId = execution.getTenantId();

        String executionId = flowableActivityEvent.getExecutionId();
        log.info("节点开始  节点id：{} 名字:{} executionId:{}", activityId, activityName, executionId);
    }

    @Override
    public void afterPropertiesSet() {
        afterPropertiesSet(FlowableEngineEventType.ACTIVITY_STARTED.toString());
    }
}
