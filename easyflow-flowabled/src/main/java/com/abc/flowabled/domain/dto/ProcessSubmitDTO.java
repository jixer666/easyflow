package com.abc.flowabled.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProcessSubmitDTO {

    private ProcessDTO flowBaseInfo;

    private List<ProcessFormItemDTO> flowFormItems;

    private NodeDTO nodeConfig;

}
