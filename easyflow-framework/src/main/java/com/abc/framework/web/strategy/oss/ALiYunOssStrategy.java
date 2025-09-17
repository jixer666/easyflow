package com.abc.framework.web.strategy.oss;

import com.abc.system.domain.dto.OssFileDto;
import com.abc.system.domain.entity.File;
import org.springframework.stereotype.Service;

/**
 * @Author: LiJunXi
 * @Description:
 * @Date: 2025-08-24  14:37
 */
@Service("aLiYunOssStrategy")
public class ALiYunOssStrategy implements OssStrategy {

    @Override
    public void saveFile(OssFileDto file) {

    }

    @Override
    public byte[] getFile(File fileEntity) {
        return new byte[0];
    }

    @Override
    public void deleteFile(File fileEntity) {

    }
}

