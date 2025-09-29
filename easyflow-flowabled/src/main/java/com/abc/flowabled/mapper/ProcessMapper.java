package com.abc.flowabled.mapper;

import java.util.List;
import com.abc.flowabled.domain.entity.Process;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 流程Mapper接口
 *
 * @author LiJunXi
 * @date 2025-09-28
 */
public interface ProcessMapper extends BaseMapper<Process> {
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
     * 删除流程
     *
     * @param processId 流程主键
     * @return 结果
     */
    int deleteProcessByProcessId(Long processId);

    /**
     * 批量删除流程
     *
     * @param processIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteProcessByProcessIds(Long[] processIds);
}
