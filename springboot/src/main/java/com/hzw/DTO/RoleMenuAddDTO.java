package com.hzw.DTO;

import com.hzw.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * @author xiaohuang
 * @date 2023/7/8
 */
@Data
public class RoleMenuAddDTO {
    private Long roleId;
    private List<Long> menuIds;
}
