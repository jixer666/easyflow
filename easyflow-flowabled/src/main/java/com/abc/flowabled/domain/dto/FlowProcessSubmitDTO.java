package com.abc.flowabled.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class FlowProcessSubmitDTO {

    private FlowProcessDTO flowBaseInfo;

    private List<FlowProcessFormItemDTO> flowFormItems;

    private NodeDTO nodeConfig;

}
