package com.hzw.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzw.comon.QueryPageParam;
import com.hzw.comon.ResponseResult;
import com.hzw.entity.Car;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzw.qo.CarQuery;
import com.hzw.vo.AnalysisVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzw
 * @since 2023-01-19
 */
public interface CarService extends IService<Car> {

    Integer count1() throws Exception;

    ResponseResult saveOrUpdateCar(Car car);

    ResponseResult findCarById(Long id);

    void deleteById(Long id) throws Exception;

    Boolean updateStates(Car car) throws Exception;

    List<AnalysisVO> typeAll() throws Exception;

    List<AnalysisVO> stateAll() throws Exception;

    Page<Car> listPage(CarQuery query) throws Exception;
}
