package com.abc.web.controller.tool;

import com.abc.common.TenantUtils;
import com.abc.common.annotation.Anonymous;
import com.abc.common.constant.FlowableConstants;
import com.abc.flowabled.domain.enums.TaskTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Anonymous
@RestController
@RequestMapping("/test/flow")
public class FlowTestController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/start")
    public void startTestFlow(@RequestParam("flowId") String flowId){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(flowId, TenantUtils.getTenantId());
        log.info("{}启动成功", processInstance.getId());
    }

    @GetMapping("/next")
    public void nextTestFlow(@RequestParam("taskId") String taskId){
        taskService.setVariableLocal(taskId, FlowableConstants.TASK_TYPE, TaskTypeEnum.PASS.getValue());
        taskService.complete(taskId);
    }




}
