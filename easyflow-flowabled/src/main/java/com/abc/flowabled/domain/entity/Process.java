package com.abc.flowabled.domain.entity;

import com.abc.common.core.domain.BaseCustomEntity;
import com.abc.common.utils.IdUtils;
import com.abc.common.utils.SecurityUtils;
import com.abc.flowabled.domain.dto.ProcessDTO;
import lombok.Builder;
import lombok.Data;
import com.abc.common.annotation.Excel;

/**
 * 流程对象 tb_process
 *
 * @author LiJunXi
 * @date 2025-09-28
 */
@Data
@Builder
public class Process extends BaseCustomEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 流程ID
     */
    private Long processId;

    /**
     * 流程名称
     */
    @Excel(name = "流程名称")
    private String processName;

    /**
     * 简要说明
     */
    @Excel(name = "简要说明")
    private String desc;

    /**
     * 分组ID
     */
    @Excel(name = "分组ID")
    private Long groupId;

    /**
     * 生成的流程ID
     */
    @Excel(name = "生成的流程ID")
    private String flowId;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;

    /**
     * 租户ID
     */
    @Excel(name = "租户ID")
    private Long tenantId;


    public static Process buildDefaultInsert(ProcessDTO flowBaseInfo, String flowId) {
        Process process = Process.builder()
                .processId(IdUtils.getId())
                .flowId(flowId)
                .processName(flowBaseInfo.getFlowProcessName())
                .desc(flowBaseInfo.getDesc())
                .groupId(flowBaseInfo.getGroupId())
                .userId(SecurityUtils.getUserId())
                .build();
        process.setCommonParams();
        return process;
    }
}
