package com.abc.flowabled.factory;

import com.abc.flowabled.strategy.FlowAllEventListenerStrategy;

import java.util.HashMap;
import java.util.Map;

public class FlowAllEventStrategyFactory {

    public static Map<String, FlowAllEventListenerStrategy> eventMap = new HashMap<>();

    public static FlowAllEventListenerStrategy getFlowAllEventListenerStrategy(String eventType) {
        return eventMap.get(eventType);
    }

    public static void register(String eventType, FlowAllEventListenerStrategy eventListener) {
        eventMap.put(eventType, eventListener);
    }

}
