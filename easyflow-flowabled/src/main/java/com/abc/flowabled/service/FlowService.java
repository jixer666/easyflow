package com.abc.flowabled.service;

import java.util.List;

import com.abc.flowabled.domain.dto.FlowProcessSubmitDTO;
import com.abc.flowabled.domain.entity.FlowProcess;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 流程Service接口
 *
 * @author LiJunXi
 * @date 2025-09-18
 */
public interface FlowService extends IService<FlowProcess> {
    /**
     * 查询流程
     *
     * @param flowId 流程主键
     * @return 流程
     */
    FlowProcess selectFlowByFlowId(Long flowId);

    /**
     * 查询流程列表
     *
     * @param flowProcess 流程
     * @return 流程集合
     */
    List<FlowProcess> selectFlowList(FlowProcess flowProcess);

    /**
     * 新增流程
     *
     * @param flowProcessSubmitDTO 流程
     * @return 结果
     */
    int insertFlow(FlowProcessSubmitDTO flowProcessSubmitDTO);

    /**
     * 修改流程
     *
     * @param flowProcess 流程
     * @return 结果
     */
    int updateFlow(FlowProcess flowProcess);

    /**
     * 批量删除流程
     *
     * @param flowIds 需要删除的流程主键集合
     * @return 结果
     */
    int deleteFlowByFlowIds(Long[] flowIds);

    /**
     * 删除流程信息
     *
     * @param flowId 流程主键
     * @return 结果
     */
    int deleteFlowByFlowId(Long flowId);
}
