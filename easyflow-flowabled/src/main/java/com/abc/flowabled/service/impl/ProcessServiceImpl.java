package com.abc.flowabled.service.impl;

import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.abc.common.TenantUtils;
import com.abc.common.utils.AssertUtil;
import com.abc.common.utils.DateUtils;
import com.abc.flowabled.domain.dto.ProcessDTO;
import com.abc.flowabled.domain.dto.ProcessSubmitDTO;
import com.abc.flowabled.domain.vo.ProcessVO;
import com.abc.flowabled.util.FlowableUtils;
import com.abc.flowabled.util.ModelUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.abc.flowabled.mapper.ProcessMapper;
import com.abc.flowabled.domain.entity.Process;
import com.abc.flowabled.service.ProcessService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程Service业务层处理
 *
 * @author LiJunXi
 * @date 2025-09-28
 */
@Service
public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process> implements ProcessService {

    @Autowired
    private ProcessMapper processMapper;

    @Value("${ruoyi.profile}")
    private String ossFilePath;

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 查询流程
     *
     * @param processId 流程主键
     * @return 流程
     */
    @Override
    public Process selectProcessByProcessId(Long processId) {
        return processMapper.selectProcessByProcessId(processId);
    }

    /**
     * 查询流程列表
     *
     * @param process 流程
     * @return 流程
     */
    @Override
    public List<Process> selectProcessList(Process process) {
        return processMapper.selectProcessList(process);
    }

    /**
     * 新增流程
     *
     * @param process 流程
     * @return 结果
     */
    @Override
    public int insertProcess(Process process) {
        AssertUtil.isNotEmpty(process, "参数不能为空");
        return processMapper.insertProcess(process);
    }

    /**
     * 修改流程
     *
     * @param process 流程
     * @return 结果
     */
    @Override
    public int updateProcess(Process process) {
        process.setUpdateTime(DateUtils.getNowDate());
        return processMapper.updateProcess(process);
    }

    /**
     * 批量删除流程
     *
     * @param processIds 需要删除的流程主键
     * @return 结果
     */
    @Override
    public int deleteProcessByProcessIds(Long[] processIds) {
        return processMapper.deleteProcessByProcessIds(processIds);
    }

    /**
     * 删除流程信息
     *
     * @param processId 流程主键
     * @return 结果
     */
    @Override
    public int deleteProcessByProcessId(Long processId) {
        return processMapper.deleteProcessByProcessId(processId);
    }

    @Override
    @Transactional
    public void transformationAndSaveProcess(ProcessSubmitDTO processSubmitDTO) {
        checkProcessCreateParam(processSubmitDTO);
        String flowId = createAndSaveBpmn(processSubmitDTO);
        System.out.println(flowId);

        insertProcess(Process.buildDefaultInsert(processSubmitDTO.getFlowBaseInfo(), flowId));

    }

    private void checkProcessCreateParam(ProcessSubmitDTO processSubmitDTO) {

    }

    private String createAndSaveBpmn(ProcessSubmitDTO processSubmitDTO) {
        String flowId = FlowableUtils.generateFlowId();
        BpmnModel bpmnModel = ModelUtil.buildBpmnModel(processSubmitDTO.getNodeConfig(), flowId, processSubmitDTO.getFlowBaseInfo().getFlowProcessName());
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        String filename = FlowableUtils.generateFlowSavePath(flowId);
        String filePath = ossFilePath + "\\" + filename;
        FileUtil.writeBytes(bpmnBytes, filePath);

        repositoryService.createDeployment()
                .tenantId(TenantUtils.getTenantIdStr())
                .addBpmnModel(filePath, bpmnModel)
                .deploy();

        return flowId;
    }


    @Override
    public List<ProcessVO> selectProcessListWithUiParam(ProcessDTO processDTO) {

        List<Process> processes = selectProcessList(BeanUtil.copyProperties(processDTO, Process.class));

        return BeanUtil.copyToList(processes, ProcessVO.class);
    }
}
