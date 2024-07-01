package com.hzw.controller;


import cn.hutool.core.lang.Assert;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hzw.DTO.OrderExcelDto;
import com.hzw.comon.ResponseResult;
import com.hzw.entity.Order;
import com.hzw.DTO.OrderDTO;
import com.hzw.exception.GlobalExceptionHandler;
import com.hzw.exception.GlobalExceptionMyHandler;
import com.hzw.qo.OrderQuery;
import com.hzw.qo.UserOrderQuery;
import com.hzw.service.OrderService;
import com.hzw.vo.OrderExcelVo;
import com.hzw.vo.OrderIdsVo;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.awt.print.Book;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hzw
 * @since 2023-01-21
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    /**
     * 预定生成订单信息
     *
     * @param orderDto
     * @return
     */
    @PostMapping("/userOrder")
    public ResponseResult userOrder(@RequestBody OrderDTO orderDto) throws GlobalExceptionHandler {
        //1.判断是否传有订单信息
        if (ObjectUtils.isEmpty(orderDto)) {
            //1.1否返回错误信息
            return ResponseResult.fail();
        }
        return ResponseResult.success(orderService.userOrder(orderDto));
    }

    /**
     * 查看发布订单数量
     *
     * @return
     */

    @GetMapping("/count")
    @PreAuthorize("hasAuthority('system:order:count')")
    public ResponseResult count0() throws GlobalExceptionHandler {
        return ResponseResult.success(orderService.count0());
    }

    @GetMapping("/count1")
    @PreAuthorize("hasAuthority('system:order:count1')")
    public ResponseResult count1() throws GlobalExceptionHandler {
        return ResponseResult.success(orderService.count1());
    }

    /**
     * 支付订单
     * @param order
     * @return
     */
    @PostMapping("/toBePaid")
    public ResponseResult toBePaid(@RequestBody Order order) {
        if (ObjectUtils.isEmpty(order)) {
            return ResponseResult.fail();
        }
        try {
            Boolean isSuccess = orderService.toBePaid(order);
            return ResponseResult.isSuccess(isSuccess, "支付");
        } catch (GlobalExceptionMyHandler g) {
            return ResponseResult.fail(g.getCustomMessage());
        }
    }

    /**
     * 取消订单
     *
     * @param order
     * @return
     */
    @PostMapping("/callOff")
    public ResponseResult callOff(@RequestBody Order order) throws GlobalExceptionHandler {
        return ResponseResult.isSuccess(orderService.callOff(order), "取消订单");
    }

    /**
     * 退款
     *
     * @param order
     * @return
     */
    @PostMapping("/refunded")
    public ResponseResult refunded(@RequestBody Order order) throws GlobalExceptionHandler {
        return ResponseResult.isSuccess(orderService.refunded(order), "退款");

    }

    /**
     * 用户删除订单
     *
     * @param id
     * @return
     */
    @GetMapping("/deleteByUser")
    public ResponseResult deleteByUser(Long id) throws GlobalExceptionHandler {
        if (ObjectUtils.isEmpty(id)) {
            return ResponseResult.fail("id不能为null");
        }
        return ResponseResult.isSuccess(orderService.deleteByUser(id), "删除订单");

    }

    /**
     * 修改订单信息
     *
     * @param order
     * @return
     */
    @PostMapping("/updateOrder")
    @PreAuthorize("hasAuthority('system:order:updateOrder')")
    public ResponseResult saveOrUpdateOrder(@RequestBody Order order) {
        if (ObjectUtils.isEmpty(order)) {
            return ResponseResult.fail("order不能为null");
        }

        try {
            Boolean isSuccess = orderService.updateOrder(order);
            return ResponseResult.isSuccess(isSuccess, "更新订单失败");
        } catch (GlobalExceptionMyHandler globalExceptionMyHandler) {
            return ResponseResult.fail(globalExceptionMyHandler.getCustomMessage());
        }
    }

    /**
     * 通过id查询订单
     *
     * @param id
     * @return
     */
    @GetMapping("/findOrderById")
    public ResponseResult findOrderById(@RequestParam Long id) {
        try {
            Assert.notNull(id, "id不能为null");
            Order order = orderService.findOrderById(id);
            return ResponseResult.success(order);
        } catch (GlobalExceptionMyHandler globalExceptionMyHandler) {
            return ResponseResult.fail(globalExceptionMyHandler.getCustomMessage());
        }

    }


    /**
     * 根据订单编号删除订单信息
     *
     * @param id
     * @return
     */
    @GetMapping("/deleteById")
    public ResponseResult deleteById(Long id) throws GlobalExceptionHandler {
        Assert.notNull(id, "id不能为null");
        Boolean isSuccess = orderService.deleteById(id);
        return ResponseResult.isSuccess(isSuccess, "删除订单");
    }


    /**
     * 根据id修改支付订单信息
     *
     * @param order
     * @return
     */
    @PostMapping("/updateStates")
    public ResponseResult updateStates(@RequestBody Order order) throws GlobalExceptionHandler {
        if (ObjectUtils.isEmpty(order)) {
            return ResponseResult.fail();
        }
        Boolean aBoolean = orderService.updateStates(order);
        return ResponseResult.isSuccess(aBoolean, "修改订单");
    }

    /**
     * 查找订单所有类型
     *
     * @return
     */
    @GetMapping("/productTypeALL")
    @PreAuthorize("hasAuthority('system:order:productTypeALL')")
    public ResponseResult typeAll() throws GlobalExceptionHandler {
        return ResponseResult.success(orderService.typeAll());

    }

    /**
     * 查找所有状态数量
     *
     * @return
     */
    @GetMapping("/stateAll")
    @PreAuthorize("hasAuthority('system:order:stateAll')")
    public ResponseResult stateAll() throws GlobalExceptionHandler {
        return ResponseResult.success(orderService.stateAll());

    }


    /**
     * 查找所有已支付的订单
     *
     * @return
     */
    @GetMapping("/findAll")
    public ResponseResult findAll() throws GlobalExceptionHandler {
        return ResponseResult.success(orderService.findAll());
    }

    /**
     * 列表条件分页查询订单
     *
     * @param query
     * @return
     */
    @PostMapping("/listPage")
    public ResponseResult listPage(@RequestBody OrderQuery query) throws GlobalExceptionHandler {
        return ResponseResult.success(orderService.listPage(query));
    }

    /**
     * 条件分页查询订单
     *
     * @param query
     * @return
     */
    @PostMapping("/orderListPage")
    public ResponseResult orderListPage(@RequestBody UserOrderQuery query) throws GlobalExceptionHandler {
        return ResponseResult.success(orderService.orderListPage(query)
        );
    }

    @PostMapping("/exportAnnotation")
    public void exportAnnotation(HttpServletResponse response, @RequestBody OrderIdsVo orderIdsVo) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            String fileName = URLEncoder.encode("订单导出", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + fileName + ".xlsx");
            //根据idList 查询订单列表
            LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Order::getId, orderIdsVo.getOrderIds());
            List<Order> orderList = orderService.list(queryWrapper);
            ArrayList<OrderExcelVo> voList = new ArrayList<>();
            for (Order order : orderList) {
                OrderExcelVo orderExcelVo = new OrderExcelVo();
                BeanUtils.copyProperties(order,orderExcelVo);
                voList.add(orderExcelVo);
            }
            // 内容的策略
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            //自动换行
            contentWriteCellStyle.setWrapped(true);
            //设置上下居中
            contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                    new HorizontalCellStyleStrategy(null, contentWriteCellStyle);
            // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
            ExcelWriterBuilder write = EasyExcel.write(response.getOutputStream(), OrderExcelVo.class);
            write.registerWriteHandler(horizontalCellStyleStrategy).sheet("sheet0")
                    .doWrite(voList);

        } catch (Exception e) {
            log.error("导出失败：{}", e.getMessage());
        }
    }

    /**
     * TODO 导入
     *
     * @param file:
     */
    @PostMapping("/importBook")
    public void importEquip(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        if (!("xlsx".equals(suffixName) || "XLSX".equals(suffixName) || "xls".equals(suffixName) || "XLS".equals(suffixName))) {
            throw new Exception("文件格式错误");
        }
        List<OrderExcelDto> equipmentExcelDtoList = EasyExcel.read(file.getInputStream()).head(OrderExcelDto.class).sheet().doReadSync();
        ArrayList<Order> orders = new ArrayList<>();
        for (OrderExcelDto orderExcelDto : equipmentExcelDtoList) {
            Order order = new Order();
            BeanUtils.copyProperties(orderExcelDto, order);
            orders.add(order);
        }
        orderService.saveBatch(orders);
        log.info("读取到bookList数据data:" + JSON.toJSONString(equipmentExcelDtoList));
        //处理读取到的数据
    }

//    @GetMapping("/exportTemplate")
//    public void exportTemplate(HttpServletResponse response) throws IOException {
//        try {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
//            String fileName = URLEncoder.encode("书籍标准模板", "UTF-8");
//            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + fileName + ".xlsx");
//
//            List<OrderExcelVo> list = new ArrayList<>();
//            OrderExcelVo bookExcelVo = new OrderExcelVo();
//            Order order = new Order();
////            Book book = orderService.getById(1);
//            bookExcelVo.setType(book.getType());
//            bookExcelVo.setDescription(book.getDescription());
//            bookExcelVo.setCreateTime(book.getCreateTime());
//            bookExcelVo.setPrice(book.getPrice());
//            bookExcelVo.setName(book.getName());
//            list.add(bookExcelVo);
//            EasyExcel.write(response.getOutputStream(), BookExcelVo.class).sheet(  "书籍导入模板").doWrite(list);
//        } catch (Exception e) {
//            // 重置response
//            response.reset();
//            response.setContentType("application/json");
//            response.setCharacterEncoding("utf-8");
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("status", "failure");
//            map.put("message", "下载文件失败" + e.getMessage());
//            response.getWriter().println(JSON.toJSONString(map));
//            logger.error("下载模板失败：{}", e.getMessage());
//        }
//    }


}
