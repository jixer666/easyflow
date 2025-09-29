package com.abc.common.core.domain;

import com.abc.common.constant.Constants;
import com.abc.common.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity自定义基类
 * 
 * @author LiJunXi
 */
@Data
public class BaseCustomEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer ver;

    private Integer status;

    public void setCommonParams() {
        this.setCreateTime(new Date());
        this.setUpdateTime(new Date());
        this.setStatus(StatusEnum.NORMAL.getStatus());
        this.setVer(Constants.DEFAULT_VER);
    }

}
