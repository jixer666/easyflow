package com.abc.system.domain.vo;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: LiJunXi
 * @Description:
 * @Date: 2025-08-04  19:42
 */
public class FileUploadDTO {

    @NotNull(message = "文件不能为空")
    private MultipartFile file;

    @NotEmpty(message = "文件MD5不能为空")
    private String fileMd5;

    @NotEmpty(message = "文件类型不能为空")
    private String fileType;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
