package com.abc.flowabled.service.impl;

import java.util.List;
import com.abc.common.utils.DateUtils;
import com.abc.flowabled.domain.dto.FlowSubmitDTO;
import com.abc.flowabled.domain.dto.NodeDTO;
import com.abc.flowabled.util.ModelUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.flowable.bpmn.model.BpmnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abc.flowabled.mapper.FlowMapper;
import com.abc.flowabled.domain.entity.Flow;
import com.abc.flowabled.service.FlowService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程Service业务层处理
 *
 * @author LiJunXi
 * @date 2025-09-18
 */
@Service
public class FlowServiceImpl extends ServiceImpl<FlowMapper, Flow> implements FlowService {

    @Autowired
    private FlowMapper flowMapper;

    /**
     * 查询流程
     *
     * @param flowId 流程主键
     * @return 流程
     */
    @Override
    public Flow selectFlowByFlowId(Long flowId) {
        return flowMapper.selectFlowByFlowId(flowId);
    }

    /**
     * 查询流程列表
     *
     * @param flow 流程
     * @return 流程
     */
    @Override
    public List<Flow> selectFlowList(Flow flow) {
        return flowMapper.selectFlowList(flow);
    }

    /**
     * 新增流程
     *
     * @param flow 流程
     * @return 结果
     */
    @Override
    @Transactional
    public int insertFlow(FlowSubmitDTO flowSubmitDTO) {
        checkFlowCreateParam(flowSubmitDTO);
        createAndSaveBpmn(flowSubmitDTO.getNodeConfig());

//        return flowMapper.insertFlow(flow);
        return 0;
    }

    private void checkFlowCreateParam(FlowSubmitDTO flowSubmitDTO) {


    }

    private void createAndSaveBpmn(NodeDTO nodeConfig) {
        BpmnModel bpmnModel = ModelUtil.buildBpmnModel(nodeConfig);

    }

    /**
     * 修改流程
     *
     * @param flow 流程
     * @return 结果
     */
    @Override
    public int updateFlow(Flow flow) {
        flow.setUpdateTime(DateUtils.getNowDate());
        return flowMapper.updateFlow(flow);
    }

    /**
     * 批量删除流程
     *
     * @param flowIds 需要删除的流程主键
     * @return 结果
     */
    @Override
    public int deleteFlowByFlowIds(Long[] flowIds) {
        return flowMapper.deleteFlowByFlowIds(flowIds);
    }

    /**
     * 删除流程信息
     *
     * @param flowId 流程主键
     * @return 结果
     */
    @Override
    public int deleteFlowByFlowId(Long flowId) {
        return flowMapper.deleteFlowByFlowId(flowId);
    }
}
