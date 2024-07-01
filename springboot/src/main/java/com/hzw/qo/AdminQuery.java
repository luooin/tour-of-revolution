package com.hzw.qo;

import lombok.Data;


@Data
public class AdminQuery extends Query{
    private String username;
    private Long userId;


}
