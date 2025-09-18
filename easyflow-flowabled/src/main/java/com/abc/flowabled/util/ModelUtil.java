package com.abc.flowabled.util;

import cn.hutool.core.util.StrUtil;
import com.abc.common.constant.FlowableConstants;
import com.abc.flowabled.domain.dto.NodeDTO;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.EventListener;
import org.flowable.bpmn.model.Process;

import java.util.*;

public class ModelUtil {


    public static BpmnModel buildBpmnModel(NodeDTO nodeConfig) {
        BpmnModel bpmnModel = new BpmnModel();
        bpmnModel.setTargetNamespace("easyflow");

        Process process = new Process();
        process.setId("1231231");
        process.setName("测试111");

        buildAllEventListener(process);
        buildAllNode(nodeConfig, process);



        return bpmnModel;


    }

    private static void buildAllEventListener(Process process) {
        List<EventListener> allEventListeners = new ArrayList<>();
        EventListener eventListener = new EventListener();
        eventListener.setImplementationType("class");
        // todo
        // eventListener.setImplementation();
        allEventListeners.add(eventListener);
        process.setEventListeners(allEventListeners);
    }


    private static void buildAllNode(NodeDTO nodeConfig, Process process) {

        List<FlowElement> flowElements = buildNode(nodeConfig);
        for (FlowElement flowElement : flowElements) {
            process.addFlowElement(flowElement);
        }

    }

    private static List<FlowElement> buildNode(NodeDTO node) {
        List<FlowElement> flowElements = new ArrayList<>();
        if (!NodeUtil.isNode(node)) {
            return flowElements;
        }

        if (node.isRootNode()) {
            flowElements.addAll(buildStartNode(node));
        }
        if (node.isEndNode()) {
            flowElements.addAll(buildEndNode(node));
        }
        if (node.isRootNode()) {
            flowElements.addAll(buildStartNode(node));
        }
        if (node.isRootNode()) {
            flowElements.addAll(buildStartNode(node));
        }


        return flowElements;
    }


    private static List<FlowElement> buildStartNode(NodeDTO node) {
        List<FlowElement> flowElements = new ArrayList<>();

        // 创建开始事件
        StartEvent startEvent = new StartEvent();
        startEvent.setId(node.getId());
        startEvent.setName(node.getNodeName());
        flowElements.add(startEvent);
        // 创建时间监听器
        FlowableListener createListener = new FlowableListener();
        createListener.setImplementationType("class");
        createListener.setImplementation(startEvent.getName());
        createListener.setEvent(FlowableConstants.CREATE_EVENT);
        // 创建一个自动完成的用户任务
        NodeDTO rootUserTaskNode = new NodeDTO();
        rootUserTaskNode.setId(getUserTaskNameByNodeId(node.getId()));
        rootUserTaskNode.setNodeName(FlowableConstants.FA_QI_REN);
        UserTask userTask = buildUserTask(rootUserTaskNode, node.getId(), createListener);
        // 自动跳过
        userTask.setSkipExpression(StrUtil.format("${true}"));
        // todo 配置多实例会签任务

        flowElements.add(userTask);

        return flowElements;
    }

    private static UserTask buildUserTask(NodeDTO node, String originNodeId, FlowableListener... flowableListeners) {
        UserTask userTask = new UserTask();
        userTask.setId(node.getId());
        userTask.setName(node.getNodeName());
        // 创建用户事件监听器
        if (Objects.nonNull(flowableListeners)) {
            userTask.setTaskListeners(Arrays.asList(flowableListeners));
        }
        // 存放自定义数据
        if (StrUtil.isNotEmpty(originNodeId)) {
            userTask.setExtensionElements(FlowableUtils.generateFlowNodeIdExtensionMap(originNodeId));
        }
        return userTask;
    }


    private static List<FlowElement> buildEndNode(NodeDTO node) {
        List<FlowElement> flowElements = new ArrayList<>();

        return flowElements;
    }


    private static String getUserTaskNameByNodeId(String nodeId) {
        return String.format("{}_user_task", nodeId);
    }
}
