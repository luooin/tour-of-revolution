package com.hzw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hzw.comon.QueryPageParam;
import com.hzw.comon.ResponseResult;
import com.hzw.entity.Strategy;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzw.entity.StrategyNum;
import com.hzw.exception.GlobalExceptionHandler;
import com.hzw.qo.Query;
import com.hzw.qo.StrategyQuery;
import org.apache.ibatis.annotations.Update;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzw
 * @since 2023-01-20
 */
public interface StrategyService extends IService<Strategy> {


     ResponseResult likeStrategy(Long id) throws GlobalExceptionHandler;

    Boolean saveOrUpdateStrategy(Strategy strategy) throws GlobalExceptionHandler;

    Strategy findStrategyById(Long id) throws GlobalExceptionHandler;

    Boolean deleteById(Long id) throws GlobalExceptionHandler;

    Boolean updateStates(Strategy strategy) throws GlobalExceptionHandler;


    List<Map<String, Object>> stateAll() throws GlobalExceptionHandler;

    List<Strategy> findAllUser() throws GlobalExceptionHandler;

    IPage<Strategy> listPage(StrategyQuery query) throws GlobalExceptionHandler;


    ResponseResult collectStrategy(Long id) throws GlobalExceptionHandler;

    IPage<Strategy> listMyStrategy(StrategyQuery query) throws GlobalExceptionHandler;

    ResponseResult getProduct(Integer productType) throws GlobalExceptionHandler;

    List<Strategy> listRanking() throws GlobalExceptionHandler;
}
