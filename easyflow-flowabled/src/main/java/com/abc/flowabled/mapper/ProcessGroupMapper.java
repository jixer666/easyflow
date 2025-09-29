package com.abc.flowabled.mapper;

import java.util.List;

import com.abc.flowabled.domain.dto.ProcessGroupDTO;
import com.abc.flowabled.domain.entity.ProcessGroup;
import com.abc.flowabled.domain.vo.ProcessGroupVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 流程分组Mapper接口
 *
 * @author LiJunXi
 * @date 2025-09-29
 */
public interface ProcessGroupMapper extends BaseMapper<ProcessGroup> {
    /**
     * 查询流程分组
     *
     * @param groupId 流程分组主键
     * @return 流程分组
     */
    ProcessGroup selectProcessGroupByGroupId(Long groupId);

    /**
     * 查询流程分组列表
     *
     * @param processGroup 流程分组
     * @return 流程分组集合
     */
    List<ProcessGroup> selectProcessGroupList(ProcessGroup processGroup);

    /**
     * 新增流程分组
     *
     * @param processGroup 流程分组
     * @return 结果
     */
    int insertProcessGroup(ProcessGroup processGroup);

    /**
     * 修改流程分组
     *
     * @param processGroup 流程分组
     * @return 结果
     */
    int updateProcessGroup(ProcessGroup processGroup);

    /**
     * 删除流程分组
     *
     * @param groupId 流程分组主键
     * @return 结果
     */
    int deleteProcessGroupByGroupId(Long groupId);

    /**
     * 批量删除流程分组
     *
     * @param groupIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteProcessGroupByGroupIds(Long[] groupIds);

    List<ProcessGroupVO> selectProcessGroupListWithProcess(ProcessGroupDTO processGroupDTO);
}
