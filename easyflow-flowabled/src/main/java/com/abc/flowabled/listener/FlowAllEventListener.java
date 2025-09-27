package com.abc.flowabled.listener;

import com.abc.flowabled.factory.FlowAllEventStrategyFactory;
import com.abc.flowabled.strategy.FlowAllEventListenerStrategy;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.api.delegate.event.FlowableEventType;

import java.util.Collection;
import java.util.Objects;

@Slf4j
public class FlowAllEventListener implements FlowableEventListener {

    @Override
    public void onEvent(FlowableEvent flowableEvent) {
        FlowAllEventListenerStrategy flowAllEventListenerStrategy = FlowAllEventStrategyFactory.getFlowAllEventListenerStrategy(flowableEvent.getType().name());
        if (Objects.isNull(flowAllEventListenerStrategy)) {
            return;
        }
        flowAllEventListenerStrategy.handle(flowableEvent);
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return "";
    }

    @Override
    public Collection<? extends FlowableEventType> getTypes() {
        return FlowableEventListener.super.getTypes();
    }
}
