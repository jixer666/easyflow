package com.abc.system.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import com.abc.common.exception.GlobalException;
import com.abc.system.domain.entity.File;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: LiJunXi
 * @Description:
 * @Date: 2025-08-04  20:27
 */
@Slf4j
@Data
public class OssFileDto extends File {

    private byte[] fileData;

    private MultipartFile multipartFile;

    public static OssFileDto buildByFile(File fileEntity, MultipartFile file) {
        OssFileDto ossFileDto = BeanUtil.copyProperties(fileEntity, OssFileDto.class);
        try {
            ossFileDto.setFileData(file.getBytes());
            ossFileDto.setMultipartFile(file);
        } catch (Exception e){
            log.error("创建OSSFileDto对象出错，原因：" + e.getMessage());
            throw new GlobalException("创建对象出错");
        }
        return ossFileDto;
    }
}
