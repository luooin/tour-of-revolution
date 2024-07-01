package com.hzw.qo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ScenciSpotQuery extends Query {
    private String spotName;
    private Integer spotStar;
    private Double lowPrice;
    private Double highPrice;
    private Integer state;
    private Long scenicSpotId;
    private String spotAddress;



}
