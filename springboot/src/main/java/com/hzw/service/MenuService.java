package com.hzw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzw.entity.Menu;
import com.hzw.vo.MenuListVo;

import java.util.List;

/**
 * @author xiaohuang
 * @date 2023/6/26
 */
public interface MenuService extends IService<Menu> {
    MenuListVo selectList(Long roleId) throws Exception;

}
