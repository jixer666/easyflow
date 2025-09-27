package com.abc.flowabled.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskTypeEnum {

    PASS("pass"),
    REFUSE("refuse")
    ;

    private String value;

}
