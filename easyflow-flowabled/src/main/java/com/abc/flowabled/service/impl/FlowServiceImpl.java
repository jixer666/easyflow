package com.abc.flowabled.service.impl;

import java.util.List;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.abc.common.utils.DateUtils;
import com.abc.flowabled.domain.dto.FlowProcessSubmitDTO;
import com.abc.flowabled.domain.dto.NodeDTO;
import com.abc.flowabled.util.FlowableUtils;
import com.abc.flowabled.util.ModelUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.abc.flowabled.mapper.FlowMapper;
import com.abc.flowabled.domain.entity.FlowProcess;
import com.abc.flowabled.service.FlowService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程Service业务层处理
 *
 * @author LiJunXi
 * @date 2025-09-18
 */
@Service
public class FlowServiceImpl extends ServiceImpl<FlowMapper, FlowProcess> implements FlowService {

    @Autowired
    private FlowMapper flowMapper;

    @Value("${ruoyi.profile}")
    private String ossFilePath;

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 查询流程
     *
     * @param flowId 流程主键
     * @return 流程
     */
    @Override
    public FlowProcess selectFlowByFlowId(Long flowId) {
        return flowMapper.selectFlowByFlowId(flowId);
    }

    /**
     * 查询流程列表
     *
     * @param flowProcess 流程
     * @return 流程
     */
    @Override
    public List<FlowProcess> selectFlowList(FlowProcess flowProcess) {
        return flowMapper.selectFlowList(flowProcess);
    }

    /**
     * 新增流程
     *
     * @param flow 流程
     * @return 结果
     */
    @Override
    @Transactional
    public int insertFlow(FlowProcessSubmitDTO flowProcessSubmitDTO) {
        checkFlowCreateParam(flowProcessSubmitDTO);
        createAndSaveBpmn(flowProcessSubmitDTO.getNodeConfig());

//        return flowMapper.insertFlow(flow);
        return 0;
    }

    private void checkFlowCreateParam(FlowProcessSubmitDTO flowProcessSubmitDTO) {


    }

    private void createAndSaveBpmn(NodeDTO nodeConfig) {
        BpmnModel bpmnModel = ModelUtil.buildBpmnModel(nodeConfig);
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        String flowId = FlowableUtils.generateFlowId();
        String filename = FlowableUtils.generateFlowSavePath(flowId);
        String filePath = ossFilePath + "\\" + filename;
        FileUtil.writeBytes(bpmnBytes, filePath);
//
//        repositoryService.createDeployment()
//                .tenantId(null)
//                .addBpmnModel(filename, bpmnModel)
//                .deploy();
    }

    /**
     * 修改流程
     *
     * @param flowProcess 流程
     * @return 结果
     */
    @Override
    public int updateFlow(FlowProcess flowProcess) {
        flowProcess.setUpdateTime(DateUtils.getNowDate());
        return flowMapper.updateFlow(flowProcess);
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
