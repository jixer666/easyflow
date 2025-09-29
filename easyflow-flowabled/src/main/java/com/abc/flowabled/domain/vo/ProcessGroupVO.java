package com.abc.flowabled.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProcessGroupVO  {

    private Long groupId;

    private String groupName;

    private Long userId;

    private Long tenantId;

    private List<ProcessVO> processList;

}
