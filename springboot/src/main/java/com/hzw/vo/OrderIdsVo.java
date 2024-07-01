package com.hzw.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaohuang
 * @date 2023/7/22
 */
@Data
public class OrderIdsVo implements Serializable {
    private List<Long> orderIds;
}
