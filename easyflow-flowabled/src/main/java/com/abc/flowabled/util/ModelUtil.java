package com.abc.flowabled.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.abc.common.constant.FlowableConstants;
import com.abc.common.utils.IdUtils;
import com.abc.flowabled.domain.dto.NodeDTO;
import com.abc.flowabled.listener.ApprovalCreateListener;
import com.abc.flowabled.listener.FlowAllEventListener;
import com.abc.flowabled.listener.UserTaskCreateListener;
import com.abc.flowabled.task.ApproveServiceTask;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.EventListener;
import org.flowable.bpmn.model.Process;

import java.util.*;

public class ModelUtil {


    public static BpmnModel buildBpmnModel(NodeDTO nodeConfig, String flowId, String flowProcessName) {
        BpmnModel bpmnModel = new BpmnModel();
        bpmnModel.setTargetNamespace(FlowableConstants.SYSTEM_CODE);

        Process process = new Process();
        process.setId(flowId);
        process.setName(flowProcessName);

        NodeUtil.addEndNode(nodeConfig);

        // 创建所有节点的监听器
        buildAllEventListener(process);
        // 创建所有节点
        buildAllNode(nodeConfig, process, null, null);
        // 创建所有连线
        buildAllNodeSequence(nodeConfig, process);

        bpmnModel.addProcess(process);

        return bpmnModel;

    }

    private static void buildAllEventListener(Process process) {
        List<EventListener> allEventListeners = new ArrayList<>();
        EventListener eventListener = new EventListener();
        eventListener.setImplementationType(FlowableConstants.CLASS);
        eventListener.setImplementation(FlowAllEventListener.class.getCanonicalName());
        allEventListeners.add(eventListener);
        process.setEventListeners(allEventListeners);
    }


    private static void buildAllNode(NodeDTO nodeConfig, Process process, NodeDTO parentNode, String parentId) {
        if (!NodeUtil.isNode(nodeConfig)) {
            return;
        }
        List<FlowElement> flowElements = buildNode(nodeConfig);
        String firstParent = CollUtil.isEmpty(flowElements) ? parentId : flowElements.get(0).getId();
        String nextParentId = CollUtil.isEmpty(flowElements) ? parentId : flowElements.get(flowElements.size() - 1).getId();
        for (FlowElement flowElement : flowElements) {
            process.addFlowElement(flowElement);
        }
        // 设置父子节点ID
        if (NodeUtil.isNode(parentNode)) {
            nodeConfig.setParentId(parentId);
            parentNode.setChildId(firstParent);
        }
        if (nodeConfig.isExclusiveGatewayNode() || nodeConfig.isParallelGatewayNode()) {
            // 分支节点
            for (NodeDTO conditionNode : nodeConfig.getConditionNodes()) {
                buildAllNode(conditionNode, process, nodeConfig, nextParentId);
            }
        }
        buildAllNode(nodeConfig.getChildNode(), process, nodeConfig, nextParentId);
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
        createListener.setImplementation(UserTaskCreateListener.class.getCanonicalName());
        createListener.setEvent(FlowableConstants.CREATE_EVENT);

        // 创建一个自动完成的用户任务
        UserTask userTask = buildUserTask(node, node.getId(), createListener);

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
        createListener.setImplementation(ApprovalCreateListener.class.getCanonicalName());
        createListener.setImplementationType(FlowableConstants.CLASS);
        createListener.setEvent(FlowableConstants.CREATE_EVENT);

        UserTask userTask = buildUserTask(node, node.getId(), createListener);
        flowElements.add(userTask);

        // 创建服务任务
        ServiceTask serviceTask = new ServiceTask();
        serviceTask.setId(NodeUtil.getServiceTaskIdByNodeId(node.getId()));
        serviceTask.setName(NodeUtil.getServiceTaskNameByNodeName(node.getNodeName()));
        serviceTask.setImplementation(ApproveServiceTask.class.getCanonicalName());
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

        node.setChildId(NodeUtil.getBeginInclusiveGatewayIdByNodeId(node.getId()));

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

        node.setChildId(NodeUtil.getBeginParallelGatewayIdByNodeId(node.getId()));

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
        userTask.setId(NodeUtil.getUserTaskIdByNodeId(node.getId()));
        userTask.setName(NodeUtil.getUserTaskNameByNodeName(node.getNodeName()));
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

    private static SequenceFlow buildSingleSequenceFlow(NodeDTO node, String expression, String parentId, String childId) {
        SequenceFlow sequenceFlow = new SequenceFlow(parentId, childId);
        sequenceFlow.setId(NodeUtil.getSequenceFlowIdByNodeId(IdUtils.getIdStr()));
        sequenceFlow.setName(NodeUtil.getSequenceFlowNameByNodeName(node.getNodeName()));
        sequenceFlow.setConditionExpression(StrUtil.isEmpty(expression) ? FlowableConstants.DEFAULT_EXPRESSION : expression);

        return sequenceFlow;
    }


    /**
     * 问题：默认分支显示错误，条件一没有连接合并分支
     * @param node
     * @param process
     */
    private static void buildAllNodeSequence(NodeDTO node, Process process) {
        if (!NodeUtil.isNode(node)) {
            return;
        }

        if (node.isExclusiveGatewayNode() || node.isParallelGatewayNode()) {

            // 创建拆分网关连线
            process.addFlowElement(buildSingleSequenceFlow(node, null, node.getParentId(), NodeUtil.getBeginInclusiveGatewayIdByNodeId(node.getId())));

            for (NodeDTO conditionNode : node.getConditionNodes()) {
                /// todo 保存数据

                String expression = "(expressionHandler.handle(execution, 123))";

                if (NodeUtil.isNode(conditionNode.getChildNode())) {
                    process.addFlowElement(buildSingleSequenceFlow(conditionNode, expression, NodeUtil.getBeginInclusiveGatewayIdByNodeId(node.getId()), conditionNode.getChildId()));
                    buildAllNodeSequence(conditionNode.getChildNode(), process);
                    // 创建合并网连线
                    // 拿到该条分支上的最后一个节点
                    FlowElement lastFlowElement = process.getFlowElements().stream().skip(process.getFlowElements().size() - 1)
                            .findFirst()
                            .orElse(null);
                    process.addFlowElement(buildSingleSequenceFlow(conditionNode, expression, ((SequenceFlow) lastFlowElement).getTargetRef(), NodeUtil.getEndInclusiveGatewayIdByNodeId(node.getId())));
                } else {
                    // 兜底分支（拆分网关->合并网关）
                    process.addFlowElement(buildSingleSequenceFlow(conditionNode, expression, NodeUtil.getBeginInclusiveGatewayIdByNodeId(node.getId()), NodeUtil.getEndInclusiveGatewayIdByNodeId(node.getId())));
                }
            }

            // 创建合并网关到下一节点
            process.addFlowElement(buildSingleSequenceFlow(node, null, NodeUtil.getEndInclusiveGatewayIdByNodeId(node.getId()), node.getChildId()));

            buildAllNodeSequence(node.getChildNode(), process);
            return;
        }

        if (node.isRootNode()) {
            // 节点内部连线单独处理
            process.addFlowElement(buildSingleSequenceFlow(node, null, node.getId(), NodeUtil.getUserTaskIdByNodeId(node.getId())));
        } else if (node.isApproveNode()) {
            // 节点内部连线单独处理
            process.addFlowElement(buildSingleSequenceFlow(node, null, NodeUtil.getUserTaskIdByNodeId(node.getId()), NodeUtil.getServiceTaskIdByNodeId(node.getId())));
            if (NodeUtil.isNode(node.getChildNode()) && node.getChildNode().isEndNode()) {
                process.addFlowElement(buildSingleSequenceFlow(node, null,  NodeUtil.getServiceTaskIdByNodeId(node.getId()), node.getChildId()));
            }
        } else if (node.isEndNode()) {

        } else {
            process.addFlowElement(buildSingleSequenceFlow(node, null, node.getId(), node.getChildId()));
        }


        buildAllNodeSequence(node.getChildNode(), process);
    }


}
