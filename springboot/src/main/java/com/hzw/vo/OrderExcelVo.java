package com.hzw.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author xiaohuang
 * @date 2023/7/22
 */
@Data
@ContentStyle(horizontalAlignment = com.alibaba.excel.enums.poi.HorizontalAlignmentEnum.CENTER)
@HeadStyle(horizontalAlignment = com.alibaba.excel.enums.poi.HorizontalAlignmentEnum.CENTER)
public class OrderExcelVo {
    @ExcelProperty("订单名")
    @ColumnWidth(20)
    private String productName;
    @ExcelProperty("创建时间")
    @ColumnWidth(20)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date addTime;
    @ColumnWidth(20)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifyTime;
    @ExcelProperty("账号")
    @ColumnWidth(10)
    private String userName;
    @ExcelProperty("姓名")
    @ColumnWidth(10)
    private String name;

    @ExcelProperty("费用")
    @ColumnWidth(10)
    private Double fee;

    @ExcelProperty("类别")
    @ColumnWidth(10)
    private Integer productType;
    @ExcelProperty("订单编号")
    @ColumnWidth(25)
    private String orderCode;

    @ExcelProperty("订单日期")
    @ColumnWidth(20)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderTime;

    @ExcelProperty("联系电话")
    @ColumnWidth(10)
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
    @ExcelProperty("天数")
    @ColumnWidth(10)
    private Integer day;
}
