package com.hzw.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleAddVo {
    private List<Long> userId;
    private Long roleId;

}
