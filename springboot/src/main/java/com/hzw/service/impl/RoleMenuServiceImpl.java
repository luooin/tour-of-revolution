package com.hzw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzw.entity.RoleMenu;
import com.hzw.mapper.RoleMenuMapper;
import com.hzw.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * @author xiaohuang
 * @date 2023/7/9
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
}
