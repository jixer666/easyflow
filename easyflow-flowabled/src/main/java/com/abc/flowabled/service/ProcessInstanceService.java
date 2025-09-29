package com.abc.flowabled.service;

import java.util.List;
import com.abc.flowabled.domain.entity.ProcessInstance;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 流程实例Service接口
 *
 * @author LiJunXi
 * @date 2025-09-28
 */
public interface ProcessInstanceService extends IService<ProcessInstance> {
    /**
     * 查询流程实例
     *
     * @param processInstanceId 流程实例主键
     * @return 流程实例
     */
    ProcessInstance selectProcessInstanceByProcessInstanceId(Long processInstanceId);

    /**
     * 查询流程实例列表
     *
     * @param processInstance 流程实例
     * @return 流程实例集合
     */
    List<ProcessInstance> selectProcessInstanceList(ProcessInstance processInstance);

    /**
     * 新增流程实例
     *
     * @param processInstance 流程实例
     * @return 结果
     */
    int insertProcessInstance(ProcessInstance processInstance);

    /**
     * 修改流程实例
     *
     * @param processInstance 流程实例
     * @return 结果
     */
    int updateProcessInstance(ProcessInstance processInstance);

    /**
     * 批量删除流程实例
     *
     * @param processInstanceIds 需要删除的流程实例主键集合
     * @return 结果
     */
    int deleteProcessInstanceByProcessInstanceIds(Long[] processInstanceIds);

    /**
     * 删除流程实例信息
     *
     * @param processInstanceId 流程实例主键
     * @return 结果
     */
    int deleteProcessInstanceByProcessInstanceId(Long processInstanceId);
}
