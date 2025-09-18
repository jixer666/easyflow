package com.abc.flowabled.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class NodeDTO {

    private String nodeName;

    private NodeDTO childNode;

    private Integer type;

    private List<FlowFormItemDTO> conditionList;

    private List<NodeDTO> conditionNodes;


}
