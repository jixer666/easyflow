package com.abc.system.domain.entity;

import cn.hutool.core.bean.BeanUtil;
import com.abc.common.constant.Constants;
import com.abc.common.core.domain.BaseCustomEntity;
import com.abc.common.enums.StatusEnum;
import com.abc.common.utils.IdUtils;
import com.abc.common.utils.SecurityUtils;
import com.abc.system.domain.vo.FileUploadDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.abc.common.annotation.Excel;
import com.abc.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * OSS文件对象 tb_file
 *
 * @author LiJunXi
 * @date 2025-08-22
 */
@Data
@TableName("tb_file")
public class File extends BaseCustomEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId
    private Long fileId;

    /** 文件名 */
    @Excel(name = "文件名")
    private String filename;

    /** 文件MD5 */
    @Excel(name = "文件MD5")
    private String fileMd5;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private String fileType;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** OSS类型 */
    @Excel(name = "OSS类型")
    private Integer ossType;

    /** 大小 */
    @Excel(name = "大小")
    private Long totalSize;


    public static File buildOtherFile(File fileEntity) {
        File saveFile = BeanUtil.copyProperties(fileEntity, File.class);
        saveFile.setFileId(IdUtils.getId());
        saveFile.setUserId(SecurityUtils.getUserId());

        return saveFile;
    }

    public static File buildNewFileByReq(FileUploadDTO req, Integer ossType) {
        File fileEntity = BeanUtil.copyProperties(req, File.class);
        fileEntity.setFileId(IdUtils.getId());
        fileEntity.setOssType(ossType);
        fileEntity.setTotalSize(req.getFile().getSize());
        fileEntity.setUserId(SecurityUtils.getUserId());
        fileEntity.setFilename(req.getFile().getOriginalFilename());

        return fileEntity;
    }
}
