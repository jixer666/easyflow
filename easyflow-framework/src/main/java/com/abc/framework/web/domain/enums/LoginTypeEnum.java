package com.abc.framework.web.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    ACCOUNT(1, "accountLoginStrategy"),
    PHONE(2, "phoneLoginStrategy"),
    EMAIL(3, "emailLoginStrategy");

    private final Integer type;
    private final String loginKey;

    public static Integer getTypeByLoginKey(String loginKey) {
        return Arrays.stream(LoginTypeEnum.values()).filter(item -> item.getLoginKey().equals(loginKey)).findFirst().get().getType();
    }

}
