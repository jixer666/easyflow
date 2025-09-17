package com.abc.web.controller.common;

import com.abc.common.core.domain.ApiResult;
import com.abc.system.domain.vo.FileVO;
import com.abc.system.domain.vo.FileUploadDTO;
import com.abc.framework.web.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: LiJunXi
 * @Description:
 * @Date: 2025-08-22  21:36
 */
@Api(tags = "OSS接口")
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public ApiResult<FileVO> uploadOss(@Validated FileUploadDTO req) {
        return ApiResult.ok(ossService.uploadOss(req));
    }

    @ApiOperation("下载文件")
    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadOss(@PathVariable("fileId") Long fileId){
        return ossService.downloadOss(fileId);
    }



}
