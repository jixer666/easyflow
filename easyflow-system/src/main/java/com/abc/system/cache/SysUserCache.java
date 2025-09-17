package com.abc.system.cache;

import com.abc.common.constant.CacheConstants;
import com.abc.common.core.cache.AbstractRedisStringCache;
import com.abc.common.core.domain.entity.SysUser;
import com.abc.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: LiJunXi
 * @Description:
 * @Date: 2025-08-25  21:04
 */
@Slf4j
@Component
public class SysUserCache extends AbstractRedisStringCache<Long, SysUser> {

    @Autowired
    private ISysUserService sysUserService;

    @Override
    protected String getKey(Long id) {
        return CacheConstants.USER_INFO + id;
    }

    @Override
    protected Long getExpireSeconds() {
        return CacheConstants.USER_INFO_EXPIRE;
    }

    @Override
    protected Map<Long, SysUser> load(List<Long> ids) {
        List<SysUser> list = sysUserService.selectSysUserByIds(ids);
        return list.stream().collect(Collectors.toMap(SysUser::getUserId, Function.identity()));
    }

}
