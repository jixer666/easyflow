package com.abc.flowabled.domain.entity;

import lombok.Data;
import com.abc.common.annotation.Excel;
import com.abc.common.core.domain.BaseEntity;

/**
 * 流程对象 tb_flow
 *
 * @author LiJunXi
 * @date 2025-09-18
 */
@Data
public class FlowProcess extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 流程ID */
    private Long flowId;

    /** 流程名称 */
    @Excel(name = "流程名称")
    private String flowProcessName;

    /** 简要说明 */
    @Excel(name = "简要说明")
    private String desc;

    /** 分组ID */
    @Excel(name = "分组ID")
    private Long groupId;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 版本号 */
    @Excel(name = "版本号")
    private Integer ver;


}
