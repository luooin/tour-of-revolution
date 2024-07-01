package com.hzw.DTO;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author xiaohuang
 * @date 2023/7/22
 */
@Data
@ContentStyle(horizontalAlignment = com.alibaba.excel.enums.poi.HorizontalAlignmentEnum.CENTER)
@HeadStyle(horizontalAlignment = com.alibaba.excel.enums.poi.HorizontalAlignmentEnum.CENTER)
public class OrderExcelDto {
    // @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty("创建时间")
    @ColumnWidth(20)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    @ExcelProperty("删除标志")
    @ColumnWidth(20)
    private Integer deleteStatus;
    @ColumnWidth(20)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    @ExcelProperty("用户名")
    @ColumnWidth(20)
    private String userName;
    @ExcelProperty("姓名")
    @ColumnWidth(20)
    private String name;

    @ExcelProperty("费用")
    @ColumnWidth(20)
    private Double fee;

    @ExcelProperty("类别")
    @ColumnWidth(20)
    private Integer productType;

//    @ApiModelProperty(value = "状态")
//    @TableField("STATE")
//    private Integer state;

    @ApiModelProperty(value = "订单编号")
    @TableField("ORDER_CODE")
    private String orderCode;

    @ExcelProperty("订单日期")
    @ColumnWidth(20)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    @ExcelProperty("联系电话")
    @ColumnWidth(20)
    private String linkTel;

    @ExcelProperty("人数")
    @ColumnWidth(10)
    private Integer peopleCount;

    @ExcelProperty("特殊要求")
    @ColumnWidth(20)
    private String requirement;

    @ExcelProperty("退款日期")
    @ColumnWidth(20)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date refundTime;

    @ExcelProperty("支付日期")
    @ColumnWidth(20)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pay_time;
    @ExcelProperty("支付日期")
    @ColumnWidth(10)
    private Integer day;
}
