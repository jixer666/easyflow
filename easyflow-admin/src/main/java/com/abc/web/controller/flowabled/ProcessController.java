package com.abc.web.controller.flowabled;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.abc.common.core.domain.ApiResult;
import com.abc.flowabled.domain.dto.ProcessDTO;
import com.abc.flowabled.domain.dto.ProcessSubmitDTO;
import com.abc.flowabled.domain.entity.Process;
import com.abc.flowabled.domain.vo.ProcessVO;
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
import com.abc.flowabled.service.ProcessService;
import com.abc.common.utils.poi.ExcelUtil;
import com.abc.common.core.page.TableDataInfo;

/**
 * 流程Controller
 *
 * @author LiJunXi
 * @date 2025-09-28
 */
@RestController
@RequestMapping("/flowabled/process")
public class ProcessController extends BaseController {
    @Autowired
    private ProcessService processService;

    /**
     * 查询流程列表
     */
    @PreAuthorize("@ss.hasPermi('flowabled:process:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProcessDTO processDTO) {
        startPage();
        List<ProcessVO> list = processService.selectProcessListWithUiParam(processDTO);
        return getDataTable(list);
    }

    /**
     * 导出流程列表
     */
    @PreAuthorize("@ss.hasPermi('flowabled:process:export')")
    @Log(title = "流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Process process) {
        List<Process> list = processService.selectProcessList(process);
        ExcelUtil<Process> util = new ExcelUtil<Process>(Process.class);
        util.exportExcel(response, list, "流程数据");
    }

    /**
     * 获取流程详细信息
     */
    @PreAuthorize("@ss.hasPermi('flowabled:process:query')")
    @GetMapping(value = "/{processId}")
    public AjaxResult getInfo(@PathVariable("processId") Long processId) {
        return success(processService.selectProcessByProcessId(processId));
    }

    /**
     * 新增流程
     */
    @PreAuthorize("@ss.hasPermi('flowabled:process:add')")
    @Log(title = "流程", businessType = BusinessType.INSERT)
    @PostMapping
    public ApiResult add(@RequestBody ProcessSubmitDTO processSubmitDTO) {
        processService.transformationAndSaveProcess(processSubmitDTO);
        return ApiResult.ok();
    }

    /**
     * 修改流程
     */
    @PreAuthorize("@ss.hasPermi('flowabled:process:edit')")
    @Log(title = "流程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Process process) {
        return toAjax(processService.updateProcess(process));
    }

    /**
     * 删除流程
     */
    @PreAuthorize("@ss.hasPermi('flowabled:process:remove')")
    @Log(title = "流程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{processIds}")
    public AjaxResult remove(@PathVariable Long[] processIds) {
        return toAjax(processService.deleteProcessByProcessIds(processIds));
    }
}
