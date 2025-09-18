package com.abc.flowabled.mapper;

import java.util.List;
import com.abc.flowabled.domain.entity.Flow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 流程Mapper接口
 *
 * @author LiJunXi
 * @date 2025-09-18
 */
public interface FlowMapper extends BaseMapper<Flow> {
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
     * @param flow 流程
     * @return 结果
     */
    int insertFlow(Flow flow);

    /**
     * 修改流程
     *
     * @param flow 流程
     * @return 结果
     */
    int updateFlow(Flow flow);

    /**
     * 删除流程
     *
     * @param flowId 流程主键
     * @return 结果
     */
    int deleteFlowByFlowId(Long flowId);

    /**
     * 批量删除流程
     *
     * @param flowIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteFlowByFlowIds(Long[] flowIds);
}
