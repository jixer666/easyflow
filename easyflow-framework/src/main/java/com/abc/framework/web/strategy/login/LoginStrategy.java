package com.abc.framework.web.strategy.login;

import com.abc.common.core.domain.model.LoginBody;
import com.abc.common.core.domain.model.LoginUser;
import com.abc.common.core.domain.model.VerificationCodeBody;

public interface LoginStrategy {

    LoginUser authenticate(LoginBody loginBody);

    void preCheck(LoginBody loginBody);

    VerificationCodeBody sendVerificationCode(LoginBody loginBody);

}
