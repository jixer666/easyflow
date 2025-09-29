package com.abc.flowabled.service.impl;

import com.abc.common.constant.FlowableConstants;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("multiInstanceHandler")
public class MultiInstanceHandler {


    /**
     * 处理节点是谁发起的，一般情况自动完成
     * @return
     */
    public List<String> resolveStarAssignee(DelegateExecution execution) {
        List<String> assignees = new ArrayList<>();
        assignees.add(execution.getVariable(FlowableConstants.ASSIGNEE_USER).toString());

        return assignees;
    }

    public boolean completionCondition(DelegateExecution execution) {
        return true;
    }

}
