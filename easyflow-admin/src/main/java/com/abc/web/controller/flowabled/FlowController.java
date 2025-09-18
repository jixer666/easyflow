package com.abc.web.controller.flowabled;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.abc.flowabled.domain.dto.FlowSubmitDTO;
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
import com.abc.flowabled.domain.entity.Flow;
import com.abc.flowabled.service.FlowService;
import com.abc.common.utils.poi.ExcelUtil;
import com.abc.common.core.page.TableDataInfo;

/**
 * 流程Controller
 *
 * @author LiJunXi
 * @date 2025-09-18
 */
@RestController
@RequestMapping("/flowabled/flow")
public class FlowController extends BaseController {
    @Autowired
    private FlowService flowService;

    /**
     * 查询流程列表
     */
    @PreAuthorize("@ss.hasPermi('flowabled:flow:list')")
    @GetMapping("/list")
    public TableDataInfo list(Flow flow) {
        startPage();
        List<Flow> list = flowService.selectFlowList(flow);
        return getDataTable(list);
    }

    /**
     * 导出流程列表
     */
    @PreAuthorize("@ss.hasPermi('flowabled:flow:export')")
    @Log(title = "流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Flow flow) {
        List<Flow> list = flowService.selectFlowList(flow);
        ExcelUtil<Flow> util = new ExcelUtil<Flow>(Flow.class);
        util.exportExcel(response, list, "流程数据");
    }

    /**
     * 获取流程详细信息
     */
    @PreAuthorize("@ss.hasPermi('flowabled:flow:query')")
    @GetMapping(value = "/{flowId}")
    public AjaxResult getInfo(@PathVariable("flowId") Long flowId) {
        return success(flowService.selectFlowByFlowId(flowId));
    }

    /**
     * 新增流程
     */
    @PreAuthorize("@ss.hasPermi('flowabled:flow:add')")
    @Log(title = "流程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FlowSubmitDTO flowSubmitDTO) {
        return toAjax(flowService.insertFlow(flowSubmitDTO));
    }

    /**
     * 修改流程
     */
    @PreAuthorize("@ss.hasPermi('flowabled:flow:edit')")
    @Log(title = "流程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Flow flow) {
        return toAjax(flowService.updateFlow(flow));
    }

    /**
     * 删除流程
     */
    @PreAuthorize("@ss.hasPermi('flowabled:flow:remove')")
    @Log(title = "流程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{flowIds}")
    public AjaxResult remove(@PathVariable Long[] flowIds) {
        return toAjax(flowService.deleteFlowByFlowIds(flowIds));
    }


}
