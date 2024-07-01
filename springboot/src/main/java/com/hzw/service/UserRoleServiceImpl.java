package com.hzw.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzw.entity.UserRole;
import com.hzw.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
 * @author xiaohuang
 * @date 2023/7/23
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
