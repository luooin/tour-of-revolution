package com.hzw.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import org.slf4j.Logger;
import java.util.List;

/**
 * @author xiaohuang
 * @date 2024/1/14
 */
public class ExcelUtil {
    private final static Logger logger= LoggerFactory.getLogger(ExcelUtil.class);
    public static void exportMergeExcel(String fileName, HttpServletResponse response, List<Object> excelList,Class excel) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        // 设置表头样式
        WriteCellStyle headStyle = new WriteCellStyle();
        headStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 设置表格内容样式
        WriteCellStyle bodyStyle = new WriteCellStyle();
        bodyStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 拿到表格处理对象
        ExcelWriter writer = EasyExcel.write(response.getOutputStream())
                .needHead(true)
                .excelType(ExcelTypeEnum.XLSX)
                // 设置需要待合并的行和列。参数1：数值数组，指定需要合并的列；参数2：数值，指定从第几行开始合并
                .registerWriteHandler(new ExcelMergeCustomerCellHandler(new int[]{1,2}, 1))
                .registerWriteHandler(new HorizontalCellStyleStrategy(headStyle, bodyStyle))
                .build();
        // 设置表格sheet样式,并写入excel
        WriteSheet sheet = EasyExcel.writerSheet("环境数据台账").head(excel).sheetNo(1).build();
        writer.write(excelList, sheet);
        writer.finish();
    }
    public static void export(List<Object> exportList,Class excel,String fileName, HttpServletResponse response){
    try {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");

        EasyExcel.write(response.getOutputStream(),excel).sheet("台账").doWrite(exportList);
    } catch (Exception e) {
        logger.error("台账导出 ", e);
    }
}
}
