package com.abc.flowabled.util;

import cn.hutool.core.collection.CollUtil;
import com.abc.common.constant.FlowableConstants;
import org.flowable.bpmn.model.ExtensionElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowableUtils {

    /**
     * 构建存储自定义扩展数据和结构的容器
     * @param nodeId
     * @return
     */
    public static Map<String, List<ExtensionElement>> generateFlowNodeIdExtensionMap(String nodeId) {
        Map<String, List<ExtensionElement>> extensionElements = new HashMap<>();
        ExtensionElement extensionElement = generateFlowNodeIdExtension(nodeId);
        extensionElements.put(FlowableConstants.SYSTEM_CODE, CollUtil.newArrayList(extensionElement));
        return extensionElements;

    }

    private static ExtensionElement generateFlowNodeIdExtension(String nodeId) {
        ExtensionElement extensionElement = new ExtensionElement();
        extensionElement.setElementText(nodeId);
        extensionElement.setName("nodeId");
        extensionElement.setNamespacePrefix("flowable");
        extensionElement.setNamespace("nodeId");
        return extensionElement;
    }




}
