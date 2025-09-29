package com.abc.flowabled.service.impl;

import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component("expressionHandler")
public class ExpressionHandler {

    public Boolean handle(DelegateExecution execution, Integer uniqueId) {
        return true;
    }
}
