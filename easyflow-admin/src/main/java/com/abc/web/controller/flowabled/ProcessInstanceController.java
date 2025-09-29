package com.abc.web.controller.flowabled;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.abc.flowabled.domain.entity.ProcessInstance;
import com.abc.flowabled.service.ProcessInstanceService;
import com.abc.common.utils.poi.ExcelUtil;
import com.abc.common.core.page.TableDataInfo;

/**
 * 流程实例Controller
 *
 * @author LiJunXi
 * @date 2025-09-28
 */
@RestController
@RequestMapping("/flowabled/processInstance")
public class ProcessInstanceController extends BaseController {
    @Autowired
    private ProcessInstanceService processInstanceService;

    /**
     * 查询流程实例列表
     */
    @PreAuthorize("@ss.hasPermi('flowabled:processInstance:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProcessInstance processInstance) {
        startPage();
        List<ProcessInstance> list = processInstanceService.selectProcessInstanceList(processInstance);
        return getDataTable(list);
    }

    /**
     * 导出流程实例列表
     */
    @PreAuthorize("@ss.hasPermi('flowabled:processInstance:export')")
    @Log(title = "流程实例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProcessInstance processInstance) {
        List<ProcessInstance> list = processInstanceService.selectProcessInstanceList(processInstance);
        ExcelUtil<ProcessInstance> util = new ExcelUtil<ProcessInstance>(ProcessInstance.class);
        util.exportExcel(response, list, "流程实例数据");
    }

    /**
     * 获取流程实例详细信息
     */
    @PreAuthorize("@ss.hasPermi('flowabled:processInstance:query')")
    @GetMapping(value = "/{processInstanceId}")
    public AjaxResult getInfo(@PathVariable("processInstanceId") Long processInstanceId) {
        return success(processInstanceService.selectProcessInstanceByProcessInstanceId(processInstanceId));
    }

    /**
     * 新增流程实例
     */
    @PreAuthorize("@ss.hasPermi('flowabled:processInstance:add')")
    @Log(title = "流程实例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProcessInstance processInstance) {
        return toAjax(processInstanceService.insertProcessInstance(processInstance));
    }

    /**
     * 修改流程实例
     */
    @PreAuthorize("@ss.hasPermi('flowabled:processInstance:edit')")
    @Log(title = "流程实例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProcessInstance processInstance) {
        return toAjax(processInstanceService.updateProcessInstance(processInstance));
    }

    /**
     * 删除流程实例
     */
    @PreAuthorize("@ss.hasPermi('flowabled:processInstance:remove')")
    @Log(title = "流程实例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{processInstanceIds}")
    public AjaxResult remove(@PathVariable Long[] processInstanceIds) {
        return toAjax(processInstanceService.deleteProcessInstanceByProcessInstanceIds(processInstanceIds));
    }
}
