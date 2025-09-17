package com.abc.common.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCodeBody {

    /**
     * 唯一标识
     */
    private String uuid;

    /**
     * 验证码
     */
    private String code;

    public VerificationCodeBody(String uuid) {
        this.uuid = uuid;
    }

    public static VerificationCodeBody buildVerificationCode(String uuid, String code) {
        return new VerificationCodeBody(uuid, code);
    }

    public static VerificationCodeBody buildVerificationCode(String uuid) {
        return new VerificationCodeBody(uuid);
    }
}
