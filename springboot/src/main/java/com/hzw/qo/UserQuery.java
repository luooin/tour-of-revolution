package com.hzw.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class UserQuery extends Query {
    private String username;
}
