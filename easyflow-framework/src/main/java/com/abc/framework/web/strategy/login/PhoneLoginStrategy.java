package com.abc.framework.web.strategy.login;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.abc.common.constant.CacheConstants;
import com.abc.common.core.domain.entity.SysUser;
import com.abc.common.core.domain.model.LoginBody;
import com.abc.common.core.domain.model.LoginUser;
import com.abc.common.core.domain.model.VerificationCodeBody;
import com.abc.common.core.redis.RedisCache;
import com.abc.common.exception.user.CaptchaException;
import com.abc.common.exception.user.CaptchaExpireException;
import com.abc.common.utils.AssertUtil;
import com.abc.framework.config.EnvironmentConfig;
import com.abc.framework.web.service.SysPasswordService;
import com.abc.framework.web.service.SysPermissionService;
import com.abc.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: LiJunXi
 * @Description:
 * @Date: 2025-08-24  18:37
 */
@Service
public class PhoneLoginStrategy implements LoginStrategy {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private EnvironmentConfig environmentConfig;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private SysPasswordService passwordService;

    @Override
    @Transactional
    public LoginUser authenticate(LoginBody loginBody) {
        SysUser sysUser = userService.selectUserByPhone(loginBody.getUsername());
        if (Objects.isNull(sysUser)) {
            sysUser = SysUser.buildDefaultUserByPhone(loginBody.getUsername());
            userService.insertUser(sysUser);
        }
        return new LoginUser(sysUser.getUserId(), sysUser.getDeptId(), sysUser, permissionService.getMenuPermission(sysUser));
    }

    @Override
    public void preCheck(LoginBody loginBody) {
        AssertUtil.isNotEmpty(loginBody.getUuid(), "UUID不能为空");
        AssertUtil.isNotEmpty(loginBody.getCode(), "验证码不能空");
        AssertUtil.isNotEmpty(loginBody.getUsername(), "电话号码不能为空");
        passwordService.checkMaxRetryCount(loginBody.getUsername());
        String trueCode = getCodeFromRedis(loginBody.getUuid());
        if (StrUtil.isEmpty(trueCode)) {
            throw new CaptchaExpireException();
        }
        if (!loginBody.getCode().equalsIgnoreCase(trueCode)) {
            passwordService.addMaxRetryCount(loginBody.getUsername());
            throw new CaptchaException();
        }
    }

    @Override
    public VerificationCodeBody sendVerificationCode(LoginBody loginBody) {
        String code = RandomUtil.randomNumbers(6);
        String uuid = RandomUtil.randomString(12);

        saveToRedis(uuid, code);

        if (environmentConfig.isDevEnv()) {
            return VerificationCodeBody.buildVerificationCode(uuid, code);
        }
        // todo

        return VerificationCodeBody.buildVerificationCode(uuid);
    }

    private void saveToRedis(String uuid, String code) {
        redisCache.setCacheObject(CacheConstants.VERIFICATION_CODE_KEY + uuid, code, CacheConstants.VERIFICATION_CODE_KEY_EXPIRE, TimeUnit.SECONDS);
    }

    private String getCodeFromRedis(String uuid) {
        return redisCache.getCacheObject(CacheConstants.VERIFICATION_CODE_KEY + uuid);
    }
}
