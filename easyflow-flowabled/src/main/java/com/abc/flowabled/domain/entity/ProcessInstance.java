package com.abc.flowabled.domain.entity;

import java.util.Date;

import com.abc.common.core.domain.BaseCustomEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.abc.common.annotation.Excel;
import com.abc.common.core.domain.BaseEntity;

/**
 * 流程实例对象 tb_process_instance
 *
 * @author LiJunXi
 * @date 2025-09-28
 */
@Data
public class ProcessInstance extends BaseCustomEntity {
    private static final long serialVersionUID = 1L;

    /** 流程实例ID */
    private Long processInstanceId;

    /** 流程ID */
    @Excel(name = "流程ID")
    private Long processId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 流程结果 */
    @Excel(name = "流程结果")
    private Integer result;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

}
