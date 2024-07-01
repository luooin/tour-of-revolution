package com.hzw.qo;

import lombok.Data;
@Data
public class InsuranceQuery extends Query{
    private String title;
    private Double price;
    private Integer type;
    private Integer state;

}
