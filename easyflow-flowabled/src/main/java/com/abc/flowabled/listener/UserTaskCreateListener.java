package com.abc.flowabled.listener;

import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.task.service.delegate.TaskListener;

/**
 * 发起人用户任务节点
 */
public class UserTaskCreateListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("开始发起人用户任务节点");
    }
}
