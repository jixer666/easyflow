package com.abc.system.domain.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeptTreeVO {

    private List<DeptVO> childDepartments;

    private List<UserVO> employees;

    private List<DeptVO> titleDepartments;

}
