package com.hzw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hzw.DTO.RoleMenuAddDTO;
import com.hzw.DTO.UserRoleAddVo;
import com.hzw.DTO.UserRoleDTO;
import com.hzw.comon.ResponseResult;
import com.hzw.entity.Role;
import com.hzw.entity.RoleMenu;
import com.hzw.entity.UserRole;
import com.hzw.exception.GlobalExceptionHandler;
import com.hzw.qo.RoleQuery;
import com.hzw.qo.RoleUserListQuery;
import com.hzw.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;

@RestController
@RequestMapping("role")
public class RoleController {
    private final static Logger logger= LoggerFactory.getLogger(RoleController.class);
    @Resource
    private RoleService roleService;

    /**
     * 获取角色详情
     * @param id
     * @return
     * @throws GlobalExceptionHandler
     */
    @GetMapping("getRole")
    ResponseResult getRole(Long id) throws GlobalExceptionHandler {
        Assert.notNull(id,"id不能为null");
        Role role = roleService.getRole(id);
        return ResponseResult.success(role);

    }

    /**
     * 删除角色
     * @param id
     * @return
     * @throws GlobalExceptionHandler
     */
    @GetMapping("delete")
    ResponseResult delete(Long id) throws GlobalExceptionHandler{
        Assert.notNull(id,"id不能为null");
        roleService.delete(id);
        return ResponseResult.success();
    }

    /**
     * 新增or修改角色
     * @param role
     * @return
     * @throws GlobalExceptionHandler
     */
    @PostMapping("addOrUpdateRole")
    ResponseResult addOrUpdateRole(@RequestBody Role role) throws GlobalExceptionHandler{
        if (ObjectUtils.isEmpty(role)){
            return ResponseResult.fail("role不能为空");

        }
        try {
            boolean empty = ObjectUtils.isEmpty(role.getId());
            Boolean isSuccess = roleService.addOrUpdateRole(role);
            return ResponseResult.isSuccess(isSuccess,empty?"新增":"修改");
        } catch (Exception e) {
           return ResponseResult.fail();
        }


    }

    /**
     * 是否启用
     * @return
     * @throws GlobalExceptionHandler
     */
    @PostMapping("updateState")
    ResponseResult updateState(@RequestBody Role role) throws GlobalExceptionHandler{
        if (ObjectUtils.isEmpty(role)){
            return ResponseResult.fail("role不能为null");
        }
        return ResponseResult.isSuccess(roleService.updateState(role),role.getStatus()==0L?"开启":"禁用");

    }
    /**
     * 关联用户
     */
    @PostMapping("associatedUser")
    ResponseResult associatedUser(@RequestBody UserRoleAddVo userRoleAddVo) throws Exception {
        if (ObjectUtils.isEmpty(userRoleAddVo)){
            return ResponseResult.fail("userRoleAddVo不能为null");
        }
        try {
            Boolean isSuccess = roleService.associatedUser(userRoleAddVo);
            return ResponseResult.isSuccess(isSuccess,"关联用户");
        } catch (Exception e) {
            return ResponseResult.fail(e.getMessage());
        }

    }

    /**
     * 取消关联
     * @param userRoleDTO
     * @return
     */
    @PostMapping("deleteAssociated")
    ResponseResult deleteAssociated(@RequestBody UserRoleDTO userRoleDTO) throws GlobalExceptionHandler {
        if (ObjectUtils.isEmpty(userRoleDTO)){
            return ResponseResult.fail("userRoleDTO不能为null");
        }
        Boolean isSuccess = roleService.deleteAssociated(userRoleDTO);
        return ResponseResult.isSuccess(isSuccess,"取消关联用户");
    }

    /**
     * 角色列表
     * @param roleQuery
     * @return
     */
    @GetMapping("list")
    ResponseResult list(RoleQuery roleQuery){
        IPage<Role> result = roleService.list(roleQuery);
        if (CollectionUtils.isEmpty(result.getRecords())){
            return ResponseResult.success(Collections.emptyList());
        }
        return ResponseResult.success(result.getRecords(),result.getTotal());

    }

    /**
     * 获取关联用户列表
     * @param query
     * @return
     * @throws GlobalExceptionHandler
     */
    @GetMapping("roleUserList")
    ResponseResult roleUserList(RoleUserListQuery query) throws GlobalExceptionHandler{
        IPage<UserRole> result = roleService.roleUserList(query);
        if (CollectionUtils.isEmpty(result.getRecords())){
            return ResponseResult.success(Collections.emptyList());
        }
        return ResponseResult.success(result.getRecords(),result.getTotal());

    }

    @PostMapping("volumeLicensing")
    public ResponseResult volumeLicensing(@RequestBody RoleMenuAddDTO roleMenuAddDTO) throws GlobalExceptionHandler{
        roleService.volumeLicensing(roleMenuAddDTO);
        return ResponseResult.success();
    }


}
