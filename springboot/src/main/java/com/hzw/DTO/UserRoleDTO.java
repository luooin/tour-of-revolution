package com.hzw.DTO;

import lombok.Data;

import java.util.List;

/**
 * @author xiaohuang
 * @date 2023/7/8
 */
@Data
public class UserRoleDTO {
    private List<Long> userId;
    private Long roleId;
}
