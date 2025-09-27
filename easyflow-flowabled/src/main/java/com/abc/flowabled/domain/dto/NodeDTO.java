package com.abc.flowabled.domain.dto;

import com.abc.flowabled.domain.enums.NodeTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NodeDTO implements Cloneable, Serializable {

    private String id;

    private String nodeName;

    private NodeDTO childNode;

    private Integer type;

    private List<FlowProcessFormItemDTO> conditionList;

    private List<NodeDTO> conditionNodes;

    private String parentId;

    private String childId;

    public Boolean isRootNode() {
        return this.type.equals(NodeTypeEnum.ROOT.getValue());
    }

    public Boolean isEndNode() {
        return this.type.equals(NodeTypeEnum.END.getValue());
    }

    public Boolean isApproveNode() {
        return this.type.equals(NodeTypeEnum.APPROVAL.getValue());
    }

    public Boolean isCcNode() {
        return this.type.equals(NodeTypeEnum.CC.getValue());
    }

    public Boolean isExclusiveGatewayNode() {
        return this.type.equals(NodeTypeEnum.EXCLUSIVE_GATEWAY.getValue());
    }

    public Boolean isParallelGatewayNode() {
        return this.type.equals(NodeTypeEnum.PARALLEL_GATEWAY.getValue());
    }

}
