package com.abc.system.service;

import java.util.List;
import com.abc.system.domain.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * OSS文件Service接口
 *
 * @author LiJunXi
 * @date 2025-08-22
 */
public interface FileService extends IService<File> {
    /**
     * 查询OSS文件
     *
     * @param fileId OSS文件主键
     * @return OSS文件
     */
    File selectFileByFileId(Long fileId);

    /**
     * 查询OSS文件列表
     *
     * @param file OSS文件
     * @return OSS文件集合
     */
    List<File> selectFileList(File file);

    /**
     * 新增OSS文件
     *
     * @param file OSS文件
     * @return 结果
     */
    int insertFile(File file);

    /**
     * 修改OSS文件
     *
     * @param file OSS文件
     * @return 结果
     */
    int updateFile(File file);

    /**
     * 批量删除OSS文件
     *
     * @param fileIds 需要删除的OSS文件主键集合
     * @return 结果
     */
    int deleteFileByFileIds(Long[] fileIds);

    /**
     * 删除OSS文件信息
     *
     * @param fileId OSS文件主键
     * @return 结果
     */
    int deleteFileByFileId(Long fileId);

    List<File> getByMd5(String fileMd5);

    File checkAndGet(Long fileId);
}
