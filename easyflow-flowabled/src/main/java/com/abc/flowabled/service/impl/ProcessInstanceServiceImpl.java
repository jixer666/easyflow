package com.abc.flowabled.service.impl;

import java.util.List;
import com.abc.common.utils.DateUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abc.flowabled.mapper.ProcessInstanceMapper;
import com.abc.flowabled.domain.entity.ProcessInstance;
import com.abc.flowabled.service.ProcessInstanceService;

/**
 * 流程实例Service业务层处理
 *
 * @author LiJunXi
 * @date 2025-09-28
 */
@Service
public class ProcessInstanceServiceImpl extends ServiceImpl<ProcessInstanceMapper, ProcessInstance> implements ProcessInstanceService {

    @Autowired
    private ProcessInstanceMapper processInstanceMapper;

    /**
     * 查询流程实例
     *
     * @param processInstanceId 流程实例主键
     * @return 流程实例
     */
    @Override
    public ProcessInstance selectProcessInstanceByProcessInstanceId(Long processInstanceId) {
        return processInstanceMapper.selectProcessInstanceByProcessInstanceId(processInstanceId);
    }

    /**
     * 查询流程实例列表
     *
     * @param processInstance 流程实例
     * @return 流程实例
     */
    @Override
    public List<ProcessInstance> selectProcessInstanceList(ProcessInstance processInstance) {
        return processInstanceMapper.selectProcessInstanceList(processInstance);
    }

    /**
     * 新增流程实例
     *
     * @param processInstance 流程实例
     * @return 结果
     */
    @Override
    public int insertProcessInstance(ProcessInstance processInstance) {
        processInstance.setCreateTime(DateUtils.getNowDate());
        return processInstanceMapper.insertProcessInstance(processInstance);
    }

    /**
     * 修改流程实例
     *
     * @param processInstance 流程实例
     * @return 结果
     */
    @Override
    public int updateProcessInstance(ProcessInstance processInstance) {
        processInstance.setUpdateTime(DateUtils.getNowDate());
        return processInstanceMapper.updateProcessInstance(processInstance);
    }

    /**
     * 批量删除流程实例
     *
     * @param processInstanceIds 需要删除的流程实例主键
     * @return 结果
     */
    @Override
    public int deleteProcessInstanceByProcessInstanceIds(Long[] processInstanceIds) {
        return processInstanceMapper.deleteProcessInstanceByProcessInstanceIds(processInstanceIds);
    }

    /**
     * 删除流程实例信息
     *
     * @param processInstanceId 流程实例主键
     * @return 结果
     */
    @Override
    public int deleteProcessInstanceByProcessInstanceId(Long processInstanceId) {
        return processInstanceMapper.deleteProcessInstanceByProcessInstanceId(processInstanceId);
    }
}
