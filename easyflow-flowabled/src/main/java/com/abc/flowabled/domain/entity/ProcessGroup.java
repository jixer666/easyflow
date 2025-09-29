package com.abc.flowabled.domain.entity;

import com.abc.common.TenantUtils;
import com.abc.common.constant.FlowableConstants;
import com.abc.common.core.domain.BaseCustomEntity;
import com.abc.common.utils.IdUtils;
import com.abc.common.utils.SecurityUtils;
import lombok.Builder;
import lombok.Data;
import com.abc.common.annotation.Excel;

/**
 * 流程分组对象 tb_process_group
 *
 * @author LiJunXi
 * @date 2025-09-29
 */
@Data
@Builder
public class ProcessGroup extends BaseCustomEntity {
    private static final long serialVersionUID = 1L;

    /** 分组ID */
    private Long groupId;

    /** 分组名称 */
    @Excel(name = "分组名称")
    private String groupName;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 租户ID */
    @Excel(name = "租户ID")
    private Long tenantId;


    public static ProcessGroup buildDefaultSave() {
        ProcessGroup processGroup = ProcessGroup.builder()
                .groupId(IdUtils.getId())
                .groupName(FlowableConstants.DEFAULT_GROUP_NAME)
                .userId(SecurityUtils.getUserId())
                .tenantId(TenantUtils.getTenantId())
                .build();
        processGroup.setCommonParams();
        return processGroup;
    }
}
