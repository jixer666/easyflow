package com.abc.flowabled.service;

import java.util.List;

import com.abc.flowabled.domain.dto.ProcessDTO;
import com.abc.flowabled.domain.dto.ProcessSubmitDTO;
import com.abc.flowabled.domain.entity.Process;
import com.abc.flowabled.domain.vo.ProcessVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 流程Service接口
 *
 * @author LiJunXi
 * @date 2025-09-28
 */
public interface ProcessService extends IService<Process> {
    /**
     * 查询流程
     *
     * @param processId 流程主键
     * @return 流程
     */
    Process selectProcessByProcessId(Long processId);

    /**
     * 查询流程列表
     *
     * @param process 流程
     * @return 流程集合
     */
    List<Process> selectProcessList(Process process);

    /**
     * 新增流程
     *
     * @param process 流程
     * @return 结果
     */
    int insertProcess(Process process);

    /**
     * 修改流程
     *
     * @param process 流程
     * @return 结果
     */
    int updateProcess(Process process);

    /**
     * 批量删除流程
     *
     * @param processIds 需要删除的流程主键集合
     * @return 结果
     */
    int deleteProcessByProcessIds(Long[] processIds);

    /**
     * 删除流程信息
     *
     * @param processId 流程主键
     * @return 结果
     */
    int deleteProcessByProcessId(Long processId);

    void transformationAndSaveProcess(ProcessSubmitDTO processSubmitDTO);

    List<ProcessVO> selectProcessListWithUiParam(ProcessDTO processDTO);
}
