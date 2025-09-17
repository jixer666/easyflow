package com.abc.system.service.impl;

import java.util.List;

import com.abc.common.utils.AssertUtil;
import com.abc.common.utils.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abc.system.mapper.FileMapper;
import com.abc.system.domain.entity.File;
import com.abc.system.service.FileService;

/**
 * OSS文件Service业务层处理
 *
 * @author LiJunXi
 * @date 2025-08-22
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Autowired
    private FileMapper fileMapper;

    /**
     * 查询OSS文件
     *
     * @param fileId OSS文件主键
     * @return OSS文件
     */
    @Override
    public File selectFileByFileId(Long fileId) {
        return fileMapper.selectFileByFileId(fileId);
    }

    /**
     * 查询OSS文件列表
     *
     * @param file OSS文件
     * @return OSS文件
     */
    @Override
    public List<File> selectFileList(File file) {
        return fileMapper.selectFileList(file);
    }

    /**
     * 新增OSS文件
     *
     * @param file OSS文件
     * @return 结果
     */
    @Override
    public int insertFile(File file) {
        file.setCreateTime(DateUtils.getNowDate());
        return fileMapper.insertFile(file);
    }

    /**
     * 修改OSS文件
     *
     * @param file OSS文件
     * @return 结果
     */
    @Override
    public int updateFile(File file) {
        file.setUpdateTime(DateUtils.getNowDate());
        return fileMapper.updateFile(file);
    }

    /**
     * 批量删除OSS文件
     *
     * @param fileIds 需要删除的OSS文件主键
     * @return 结果
     */
    @Override
    public int deleteFileByFileIds(Long[] fileIds) {
        return fileMapper.deleteFileByFileIds(fileIds);
    }

    /**
     * 删除OSS文件信息
     *
     * @param fileId OSS文件主键
     * @return 结果
     */
    @Override
    public int deleteFileByFileId(Long fileId) {
        return fileMapper.deleteFileByFileId(fileId);
    }

    @Override
    public List<File> getByMd5(String fileMd5) {
        AssertUtil.isNotEmpty(fileMd5, "文件MD5不能为空");
        return fileMapper.selectList(new LambdaQueryWrapper<File>()
                .eq(File::getFileMd5, fileMd5));
    }

    @Override
    public File checkAndGet(Long fileId) {
        AssertUtil.isNotEmpty(fileId, "文件ID不能为空");
        File fileEntity = fileMapper.selectById(fileId);
        AssertUtil.isNotEmpty(fileEntity, "文件不存在");
        return fileEntity;
    }
}
