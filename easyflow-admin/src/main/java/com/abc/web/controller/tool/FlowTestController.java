package com.abc.web.controller.tool;

import cn.hutool.core.collection.CollUtil;
import com.abc.common.TenantUtils;
import com.abc.common.annotation.Anonymous;
import com.abc.common.constant.FlowableConstants;
import com.abc.flowabled.domain.enums.TaskTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@Anonymous
@RestController
@RequestMapping("/test/flow")
public class FlowTestController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @GetMapping("/start")
    public void startTestFlow(@RequestParam("flowId") String flowId){
        Authentication.setAuthenticatedUserId("666");
        Map<String, Object> variables = new HashMap<>();
        variables.put(FlowableConstants.ASSIGNEE_USER, CollUtil.newArrayList(1));
        // 自动跳过
        variables.put("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(
                flowId, null, variables, TenantUtils.getTenantIdStr());
        log.info("{}启动成功", processInstance.getId());
    }


    @GetMapping("/todoList")
    public List<Map<String, Object>> getTaskList() {
        TaskQuery taskQuery = taskService.createTaskQuery()
                .taskTenantId(TenantUtils.getTenantIdStr());

        // 明确指定需要加载的字段
        List<Task> tasks = taskQuery
                .orderByTaskCreateTime().desc()
                .listPage(0, 100);

        // 在事务上下文内提取所有需要的数据
        List<Map<String, Object>> result = new ArrayList<>();
        for (Task task : tasks) {
            Map<String, Object> taskInfo = new HashMap<>();
            taskInfo.put("id", task.getId());
            taskInfo.put("name", task.getName());
            taskInfo.put("assignee", task.getAssignee());
            taskInfo.put("createTime", task.getCreateTime());
            taskInfo.put("processInstanceId", task.getProcessInstanceId());
            taskInfo.put("processDefinitionId", task.getProcessDefinitionId());
            // 添加其他需要的字段...

            result.add(taskInfo);
        }

        return result;
    }

    @GetMapping("/next")
    public void nextTestFlow(@RequestParam("taskId") String taskId){
        taskService.setVariableLocal(taskId, FlowableConstants.TASK_TYPE, TaskTypeEnum.PASS.getValue());
        taskService.complete(taskId);
    }

    @GetMapping("/his")
    public List<HistoricTaskInstance> getHistory(){
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery().taskTenantId(TenantUtils.getTenantIdStr());

        return historicTaskInstanceQuery
                .taskWithoutDeleteReason()
                .finished()
                .orderByHistoricTaskInstanceEndTime().desc()
                .listPage(0, 100);
    }


}
