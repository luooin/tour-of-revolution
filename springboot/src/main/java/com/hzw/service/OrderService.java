package com.hzw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzw.comon.ResponseResult;
import com.hzw.entity.Order;
import com.hzw.DTO.OrderDTO;
import com.hzw.exception.GlobalExceptionHandler;
import com.hzw.exception.GlobalExceptionMyHandler;
import com.hzw.qo.OrderQuery;
import com.hzw.qo.UserOrderQuery;
import com.hzw.vo.AnalysisVO;
import com.hzw.vo.OrderListV0;
import com.hzw.vo.OrderSumVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzw
 * @since 2023-01-21
 */
public interface OrderService extends IService<Order> {

    Integer count0() throws GlobalExceptionHandler;

    Integer count1() throws GlobalExceptionHandler;

    Boolean updateOrder(Order order) throws GlobalExceptionMyHandler;

    Order findOrderById(Long id) throws GlobalExceptionMyHandler;

    Boolean deleteById(Long id) throws GlobalExceptionHandler;

    Boolean updateStates(Order order) throws GlobalExceptionHandler;

    List<AnalysisVO> typeAll() throws GlobalExceptionHandler;

    List<AnalysisVO> stateAll() throws GlobalExceptionHandler;

    List<Order> findAll() throws GlobalExceptionHandler;

    OrderListV0 listPage(OrderQuery query) throws GlobalExceptionHandler;

    Long userOrder(OrderDTO orderDto) throws GlobalExceptionHandler;

    Boolean toBePaid(Order order) throws GlobalExceptionMyHandler;


    OrderListV0 orderListPage(UserOrderQuery query) throws GlobalExceptionHandler;

    Boolean callOff(Order order) throws GlobalExceptionHandler;

    /**
     * 退款
     * @param order
     * @return
     * @throws GlobalExceptionHandler
     */
    Boolean refunded(Order order) throws GlobalExceptionHandler;

    Boolean deleteByUser(Long id) throws GlobalExceptionHandler;
}
