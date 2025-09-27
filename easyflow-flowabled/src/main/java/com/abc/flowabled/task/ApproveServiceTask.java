package com.abc.flowabled.task;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * 审批任务处理器--java服务任务
 */
public class ApproveServiceTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("开始处理审批任务");


    }
}