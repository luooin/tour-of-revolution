package com.hzw.vo;

import com.hzw.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xiaohuang
 * @date 2023/7/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSumVo {
    Double orderSum;
    List<Order> orderList;
}
