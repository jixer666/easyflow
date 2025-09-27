package com.abc.flowabled.strategy;

import com.abc.flowabled.factory.FlowAllEventStrategyFactory;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;

public interface FlowAllEventListenerStrategy {

    default void afterPropertiesSet(String key) {
        FlowAllEventStrategyFactory.register(key, this);
    }

    void handle(FlowableEvent flowableEvent);

}
