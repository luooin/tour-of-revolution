package com.hzw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzw.entity.Admin;
import com.hzw.entity.Province;
import com.hzw.vo.ProvinceVo;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {

    public List<ProvinceVo> findProvinceByid();

    public List<Province> findfindProvinceAll();


}
