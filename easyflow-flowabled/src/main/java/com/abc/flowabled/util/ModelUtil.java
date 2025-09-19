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

        // todo
        buildAllEventListener(process);
        buildAllNode(nodeConfig, process);


        bpmnModel.addProcess(process);

        return bpmnModel;


    }

    private static void buildAllEventListener(Process process) {
        List<EventListener> allEventListeners = new ArrayList<>();
        EventListener eventListener = new EventListener();
        eventListener.setImplementationType(FlowableConstants.CLASS);
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

        NodeDTO childNode = nodeConfig.getChildNode();
        if (!NodeUtil.isNode(childNode)) {
            return;
        }
        if (childNode.isExclusiveGatewayNode() || childNode.isParallelGatewayNode()) {
            List<NodeDTO> conditionNodes = childNode.getConditionNodes();
            // 分支节点
            for (NodeDTO conditionNode : conditionNodes) {
                buildAllNode(conditionNode, process);
            }
        }
        buildAllNode(childNode, process);
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
            flowElements.addAll(buildEndNode(node, false));
        }
        if (node.isApproveNode()) {
            flowElements.addAll(buildApproveNode(node));
        }
        if (node.isCcNode()) {
            flowElements.addAll(buildCcNode(node));
        }
        if (node.isExclusiveGatewayNode()) {
            flowElements.addAll(buildInclusiveGatewayNode(node));
        }
        if (node.isParallelGatewayNode()) {
            flowElements.addAll(buildParallelGatewayNode(node));
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

        // 创建任务执行监听器
        FlowableListener createListener = new FlowableListener();
        createListener.setImplementationType(FlowableConstants.CLASS);
        // todo
        createListener.setImplementation("");
        createListener.setEvent(FlowableConstants.CREATE_EVENT);

        // 创建一个自动完成的用户任务
        NodeDTO rootUserTaskNode = new NodeDTO();
        rootUserTaskNode.setId(NodeUtil.getUserTaskIdByNodeId(node.getId()));
        rootUserTaskNode.setNodeName(FlowableConstants.FA_QI_REN);
        UserTask userTask = buildUserTask(rootUserTaskNode, node.getId(), createListener);

        // 自动跳过
        userTask.setSkipExpression(StrUtil.format("${true}"));

        // todo 配置多实例会签任务

        flowElements.add(userTask);

        return flowElements;
    }

    private static List<FlowElement> buildEndNode(NodeDTO node, Boolean terminateAll) {
        List<FlowElement> flowElements = new ArrayList<>();

        EndEvent endEvent = new EndEvent();
        endEvent.setId(node.getId());
        endEvent.setName(node.getNodeName());

        // 配置终止事件定义
        List<EventDefinition> eventDefinitions = new ArrayList<>();
        TerminateEventDefinition terminateEventDefinition = new TerminateEventDefinition();
        // terminateAll=true：终止整个流程实例
        // terminateAll=false：只终止当前流程分支
        terminateEventDefinition.setTerminateAll(terminateAll);
        eventDefinitions.add(terminateEventDefinition);
        endEvent.setEventDefinitions(eventDefinitions);

        flowElements.add(endEvent);

        return flowElements;
    }

    private static List<FlowElement> buildApproveNode(NodeDTO node) {
        List<FlowElement> flowElements = new ArrayList<>();

        FlowableListener createListener = new FlowableListener();
        createListener.setImplementation("");
        createListener.setImplementationType(FlowableConstants.CLASS);
        createListener.setEvent(FlowableConstants.CREATE_EVENT);

        UserTask userTask = buildUserTask(node, node.getId(), createListener);
        flowElements.add(userTask);

        // 创建服务任务
        ServiceTask serviceTask = new ServiceTask();
        serviceTask.setId(NodeUtil.getServiceTaskIdByNodeId(node.getId()));
        serviceTask.setName(NodeUtil.getServiceTaskNameByNodeName(node.getNodeName()));
        serviceTask.setImplementation("");
        serviceTask.setImplementationType(FlowableConstants.CLASS);
        serviceTask.setAsynchronous(false);
        serviceTask.setExtensionElements(FlowableUtils.generateFlowNodeIdExtensionMap(node.getId()));
        flowElements.add(serviceTask);

        // todo 多人会签

        return flowElements;
    }


    private static List<FlowElement> buildCcNode(NodeDTO node) {
        List<FlowElement> flowElements = new ArrayList<>();
        ServiceTask serviceTask = new ServiceTask();
        serviceTask.setId(NodeUtil.getServiceTaskIdByNodeId(node.getId()));
        serviceTask.setName(NodeUtil.getServiceTaskNameByNodeName(node.getNodeName()));
        serviceTask.setImplementation("");
        serviceTask.setImplementationType(FlowableConstants.CLASS);
        serviceTask.setAsynchronous(false);
        flowElements.add(serviceTask);

        return flowElements;
    }


    /**
     *         [入口网关]
     *            ↓
     *     ┌───────┴───────┐
     *     ↓               ↓
     * [分支A]         [分支B]
     *     ↓               ↓
     *     └───────┬───────┘
     *         [合并网关]
     * @param node
     * @return
     */
    private static List<FlowElement> buildInclusiveGatewayNode(NodeDTO node) {
        List<FlowElement> flowElements = new ArrayList<>();

        // 入口网关
        InclusiveGateway beginGateway = new InclusiveGateway();
        beginGateway.setId(NodeUtil.getBeginInclusiveGatewayIdByNodeId(node.getId()));
        beginGateway.setName(NodeUtil.getBeginInclusiveGatewayNameByNodeName(node.getId()));

        // 合并网关
        InclusiveGateway endGateway = new InclusiveGateway();
        endGateway.setId(NodeUtil.getEndInclusiveGatewayIdByNodeId(node.getId()));
        endGateway.setName(NodeUtil.getEndInclusiveGatewayNameByNodeName(node.getId()));

        flowElements.add(beginGateway);
        flowElements.add(endGateway);

        return flowElements;
    }


    /**
     *         [拆分网关]
     *            ↓
     *     ┌───────┴───────┐
     *     ↓               ↓
     * [分支A]         [分支B]    ← 两个分支都必须执行
     *     ↓               ↓
     *     └───────┬───────┘
     *         [合并网关]     ← 等待两个分支都完成
     *            ↓
     *       [后续流程]
     * @param node
     * @return
     */
    private static List<FlowElement> buildParallelGatewayNode(NodeDTO node) {
        List<FlowElement> flowElements = new ArrayList<>();

        // 入口网关
        ParallelGateway beginGateway = new ParallelGateway();
        beginGateway.setId(NodeUtil.getBeginParallelGatewayIdByNodeId(node.getId()));
        beginGateway.setName(NodeUtil.getBeginParallelGatewayNameByNodeName(node.getId()));

        // 合并网关
        ParallelGateway endGateway = new ParallelGateway();
        endGateway.setId(NodeUtil.getEndParallelGatewayIdByNodeId(node.getId()));
        endGateway.setName(NodeUtil.getEndParallelGatewayNameByNodeName(node.getId()));

        flowElements.add(beginGateway);
        flowElements.add(endGateway);

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




}
