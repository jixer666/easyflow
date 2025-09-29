package com.abc.flowabled.service.impl;

import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import com.abc.common.core.service.BaseServiceImpl;
import com.abc.common.utils.AssertUtil;
import com.abc.common.utils.DateUtils;
import com.abc.flowabled.domain.dto.ProcessGroupDTO;
import com.abc.flowabled.domain.vo.ProcessGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abc.flowabled.mapper.ProcessGroupMapper;
import com.abc.flowabled.domain.entity.ProcessGroup;
import com.abc.flowabled.service.ProcessGroupService;

/**
 * 流程分组Service业务层处理
 *
 * @author LiJunXi
 * @date 2025-09-29
 */
@Service
public class ProcessGroupServiceImpl extends BaseServiceImpl<ProcessGroupMapper, ProcessGroup> implements ProcessGroupService {

    @Autowired
    private ProcessGroupMapper processGroupMapper;

    /**
     * 查询流程分组
     *
     * @param groupId 流程分组主键
     * @return 流程分组
     */
    @Override
    public ProcessGroup selectProcessGroupByGroupId(Long groupId) {
        return processGroupMapper.selectProcessGroupByGroupId(groupId);
    }

    /**
     * 查询流程分组列表
     *
     * @param processGroup 流程分组
     * @return 流程分组
     */
    @Override
    public List<ProcessGroup> selectProcessGroupList(ProcessGroup processGroup) {
        return processGroupMapper.selectProcessGroupList(processGroup);
    }

    /**
     * 新增流程分组
     *
     * @param processGroup 流程分组
     * @return 结果
     */
    @Override
    public int insertProcessGroup(ProcessGroup processGroup) {
        AssertUtil.isNotEmpty(processGroup, "参数不能为空");
        return processGroupMapper.insertProcessGroup(processGroup);
    }

    /**
     * 修改流程分组
     *
     * @param processGroup 流程分组
     * @return 结果
     */
    @Override
    public int updateProcessGroup(ProcessGroupDTO processGroupDTO) {
        AssertUtil.isNotEmpty(processGroupDTO, "参数不能为空");
        AssertUtil.isNotEmpty(processGroupDTO.getGroupId(), "分组ID不能为空");

        ProcessGroup processGroup = selectProcessGroupByGroupId(processGroupDTO.getGroupId());
        AssertUtil.isNotEmpty(processGroup, "分组不存在");

        BeanUtil.copyProperties(processGroupDTO, processGroup);

        return processGroupMapper.updateProcessGroup(processGroup);
    }

    /**
     * 批量删除流程分组
     *
     * @param groupIds 需要删除的流程分组主键
     * @return 结果
     */
    @Override
    public int deleteProcessGroupByGroupIds(Long[] groupIds) {
        return processGroupMapper.deleteProcessGroupByGroupIds(groupIds);
    }

    /**
     * 删除流程分组信息
     *
     * @param groupId 流程分组主键
     * @return 结果
     */
    @Override
    public int deleteProcessGroupByGroupId(Long groupId) {
        return processGroupMapper.deleteProcessGroupByGroupId(groupId);
    }

    @Override
    public List<ProcessGroupVO> selectProcessGroupListWithUiParam(ProcessGroupDTO processGroupDTO) {

        List<ProcessGroupVO> processGroupVOS = processGroupMapper.selectProcessGroupListWithProcess(processGroupDTO);

        return pageList2CustomList(processGroupVOS, (List<ProcessGroupVO> list) -> {
            return list;
        });
    }

    @Override
    public ProcessGroupVO saveDefaultProcessGroup() {
        ProcessGroup processGroup = ProcessGroup.buildDefaultSave();
        insertProcessGroup(processGroup);

        return BeanUtil.copyProperties(processGroup, ProcessGroupVO.class);
    }
}
