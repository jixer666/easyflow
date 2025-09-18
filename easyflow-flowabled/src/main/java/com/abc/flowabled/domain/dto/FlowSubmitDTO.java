package com.abc.flowabled.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class FlowSubmitDTO {

    private FlowDTO flowBaseInfo;

    private List<FlowFormItemDTO> flowFormItems;

    private NodeDTO nodeConfig;

}
