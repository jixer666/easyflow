package com.abc.flowabled.domain.dto;

import lombok.Data;

@Data
public class FlowProcessDTO {

    private Long flowId;

    private String flowProcessName;

    private String desc;

    private Long groupId;

}
