package com.hzw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzw.comon.ResponseResult;
import com.hzw.constant.CommonConstants;
import com.hzw.entity.Menu;
import com.hzw.entity.RoleMenu;
import com.hzw.service.MenuService;
import com.hzw.service.RoleMenuService;
import com.hzw.utils.TreeUtil;
import com.hzw.vo.MenuListTreeVo;
import com.hzw.vo.MenuListVo;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaohuang
 * @date 2023/6/26
 */
@RestController
@RequestMapping("menu")
public class MenuController {
    private final static Logger logger= LoggerFactory.getLogger(MenuController.class);
    @Autowired
    private MenuService menuService;


    @GetMapping("list")
    public ResponseResult getList(Long roleId){
        try {
            return ResponseResult.success(menuService.selectList(roleId));
        } catch (Exception e) {
           logger.error("获取权限树结构失败={}",e.getMessage());
           return ResponseResult.fail(e.getMessage());
        }
    }



}
