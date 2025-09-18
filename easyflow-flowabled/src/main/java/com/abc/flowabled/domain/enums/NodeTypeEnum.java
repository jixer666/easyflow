package com.abc.flowabled.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NodeTypeEnum {

    ROOT("根节点", 0),
    END( "结束节点", -1),

    APPROVAL("审批节点", 1),
    CC("抄送节点", 2),
    EXCLUSIVE_GATEWAY( "条件分支", 4),
    PARALLEL_GATEWAY( "并行分支", 5),



    ;

    private String name;
    private Integer value;

}
