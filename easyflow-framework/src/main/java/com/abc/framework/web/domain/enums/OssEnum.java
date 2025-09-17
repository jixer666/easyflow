package com.abc.framework.web.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OssEnum {

    MINIO(1, "minioOssStrategy"),
    ALIYUN(2, "aLiYunOssStrategy");

    private final Integer type;
    private final String ossKey;

    public static Integer getTypeByOssKey(String ossKey) {
        return Arrays.stream(OssEnum.values()).filter(item -> item.getOssKey().equals(ossKey)).findFirst().get().getType();
    }

}
