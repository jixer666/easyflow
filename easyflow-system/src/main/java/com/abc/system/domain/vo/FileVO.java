package com.abc.system.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.abc.system.domain.entity.File;
import lombok.Data;

/**
 * @Author: LiJunXi
 * @Description:
 * @Date: 2025-08-04  19:40
 */
@Data
public class FileVO {

    private Long fileId;

    private String filename;

    private String fileMd5;

    private String fileType;

    private Long totalSize;

    private String downloadUrl;

    public static FileVO buildFileVO(File saveFile, String downloadUrl) {
        FileVO fileVo = BeanUtil.copyProperties(saveFile, FileVO.class);
        fileVo.setDownloadUrl(downloadUrl);

        return fileVo;
    }
}
