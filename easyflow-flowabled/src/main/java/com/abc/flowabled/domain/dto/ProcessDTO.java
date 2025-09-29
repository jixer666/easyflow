package com.abc.flowabled.domain.dto;

import lombok.Data;

@Data
public class ProcessDTO {

    private Long flowId;

    private String flowProcessName;

    private String desc;

    private Long groupId;

}
