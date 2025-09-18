package com.abc.flowabled.service;

import java.util.List;

import com.abc.flowabled.domain.dto.FlowSubmitDTO;
import com.abc.flowabled.domain.entity.Flow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 流程Service接口
 *
 * @author LiJunXi
 * @date 2025-09-18
 */
public interface FlowService extends IService<Flow> {
    /**
     * 查询流程
     *
     * @param flowId 流程主键
     * @return 流程
     */
    Flow selectFlowByFlowId(Long flowId);

    /**
     * 查询流程列表
     *
     * @param flow 流程
     * @return 流程集合
     */
    List<Flow> selectFlowList(Flow flow);

    /**
     * 新增流程
     *
     * @param flowSubmitDTO 流程
     * @return 结果
     */
    int insertFlow(FlowSubmitDTO flowSubmitDTO);

    /**
     * 修改流程
     *
     * @param flow 流程
     * @return 结果
     */
    int updateFlow(Flow flow);

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
