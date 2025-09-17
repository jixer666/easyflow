package com.abc.framework.web.service;

import com.abc.common.core.domain.model.LoginBody;
import com.abc.common.core.domain.model.LoginUser;
import com.abc.common.core.domain.model.VerificationCodeBody;
import com.abc.common.utils.AssertUtil;
import com.abc.framework.web.factory.LoginFactory;
import com.abc.framework.web.strategy.login.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.abc.common.core.domain.entity.SysUser;
import com.abc.common.utils.DateUtils;
import com.abc.common.utils.ip.IpUtils;
import com.abc.system.service.ISysUserService;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysUserService userService;

    /**
     * 登录验证
     */
    public String login(LoginBody loginBody) {
        LoginStrategy loginStrategy = getLoginStrategy(loginBody.getLoginType());
        loginStrategy.preCheck(loginBody);
        LoginUser loginUser = loginStrategy.authenticate(loginBody);
        recordLoginInfo(loginUser.getUserId());
        return tokenService.createToken(loginUser);
    }

    public VerificationCodeBody sendVerificationCode(LoginBody loginBody) {
        LoginStrategy loginStrategy = getLoginStrategy(loginBody.getLoginType());
        return loginStrategy.sendVerificationCode(loginBody);
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr());
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }


    public LoginStrategy getLoginStrategy(Integer loginType) {
        AssertUtil.isNotEmpty(loginType, "登录类型为空");
        LoginStrategy loginStrategy = LoginFactory.getLoginStrategy(loginType);
        AssertUtil.isNotEmpty(loginStrategy, "登录类型不存在");
        return loginStrategy;
    }


}
