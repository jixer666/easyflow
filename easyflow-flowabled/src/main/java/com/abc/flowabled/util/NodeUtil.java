package com.abc.flowabled.util;

import com.abc.flowabled.domain.dto.NodeDTO;

import java.util.Objects;

public class NodeUtil {

    public static Boolean isNode(NodeDTO nodeDTO) {
        return Objects.nonNull(nodeDTO);
    }

    public static String getUserTaskIdByNodeId(String nodeId) {
        return String.format("user_task_{}", nodeId);
    }

    public static String getServiceTaskIdByNodeId(String nodeId) {
        return String.format("service_task_id_%s", nodeId);
    }

    public static String getServiceTaskNameByNodeName(String nodeName) {
        return String.format("服务任务_%s", nodeName);
    }

    public static String getBeginInclusiveGatewayIdByNodeId(String nodeId) {
        return String.format("begin_inclusive_gateway_%s", nodeId);
    }

    public static String getBeginInclusiveGatewayNameByNodeName(String nodeName) {
        return String.format("包容性入口网关_%s", nodeName);
    }

    public static String getEndInclusiveGatewayIdByNodeId(String nodeId) {
        return String.format("end_inclusive_gateway_%s", nodeId);
    }

    public static String getEndInclusiveGatewayNameByNodeName(String nodeName) {
        return String.format("包容性合并网关_%s", nodeName);
    }

    public static String getBeginParallelGatewayIdByNodeId(String nodeId) {
        return String.format("begin_parallel_gateway_%s", nodeId);
    }

    public static String getBeginParallelGatewayNameByNodeName(String nodeName) {
        return String.format("并行入口网关_%s", nodeName);
    }

    public static String getEndParallelGatewayIdByNodeId(String nodeId) {
        return String.format("end_parallel_gateway_%s", nodeId);
    }

    public static String getEndParallelGatewayNameByNodeName(String nodeName) {
        return String.format("并行合并网关_%s", nodeName);
    }
}