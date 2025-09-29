package com.abc.web.controller.flowabled;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.abc.common.core.domain.ApiResult;
import com.abc.flowabled.domain.dto.ProcessGroupDTO;
import com.abc.flowabled.domain.vo.ProcessGroupVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.abc.common.annotation.Log;
import com.abc.common.core.controller.BaseController;
import com.abc.common.core.domain.AjaxResult;
import com.abc.common.enums.BusinessType;
import com.abc.flowabled.domain.entity.ProcessGroup;
import com.abc.flowabled.service.ProcessGroupService;
import com.abc.common.utils.poi.ExcelUtil;
import com.abc.common.core.page.TableDataInfo;

/**
 * 流程分组Controller
 *
 * @author LiJunXi
 * @date 2025-09-29
 */
@RestController
@RequestMapping("/flowabled/processGroup")
public class ProcessGroupController extends BaseController {
    @Autowired
    private ProcessGroupService processGroupService;

    /**
     * 查询流程分组列表
     */
    @PreAuthorize("@ss.hasPermi('flowabled:processGroup:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProcessGroupDTO processGroupDTO) {
        startPage();
        List<ProcessGroupVO> list = processGroupService.selectProcessGroupListWithUiParam(processGroupDTO);
        return getDataTable(list);
    }

    /**
     * 导出流程分组列表
     */
    @PreAuthorize("@ss.hasPermi('flowabled:processGroup:export')")
    @Log(title = "流程分组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProcessGroup processGroup) {
        List<ProcessGroup> list = processGroupService.selectProcessGroupList(processGroup);
        ExcelUtil<ProcessGroup> util = new ExcelUtil<ProcessGroup>(ProcessGroup.class);
        util.exportExcel(response, list, "流程分组数据");
    }

    /**
     * 获取流程分组详细信息
     */
    @PreAuthorize("@ss.hasPermi('flowabled:processGroup:query')")
    @GetMapping(value = "/{groupId}")
    public AjaxResult getInfo(@PathVariable("groupId") Long groupId) {
        return success(processGroupService.selectProcessGroupByGroupId(groupId));
    }

    /**
     * 新增流程分组
     */
    @PreAuthorize("@ss.hasPermi('flowabled:processGroup:add')")
    @Log(title = "流程分组", businessType = BusinessType.INSERT)
    @PostMapping
    public ApiResult<ProcessGroupVO> add() {

        return ApiResult.ok(processGroupService.saveDefaultProcessGroup());
    }

    /**
     * 修改流程分组
     */
    @PreAuthorize("@ss.hasPermi('flowabled:processGroup:edit')")
    @Log(title = "流程分组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProcessGroupDTO processGroupDTO) {
        return toAjax(processGroupService.updateProcessGroup(processGroupDTO));
    }

    /**
     * 删除流程分组
     */
    @PreAuthorize("@ss.hasPermi('flowabled:processGroup:remove')")
    @Log(title = "流程分组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{groupIds}")
    public AjaxResult remove(@PathVariable Long[] groupIds) {
        return toAjax(processGroupService.deleteProcessGroupByGroupIds(groupIds));
    }
}
