package com.hzw.controller;


import cn.hutool.core.lang.Assert;
import com.hzw.comon.ResponseResult;
import com.hzw.entity.Notic;
import com.hzw.service.NoticService;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author hzw
 * @since 2023-02-01
 */
@RestController
@RequestMapping("/notic")
public class NoticController {
    private final static Logger log= LoggerFactory.getLogger(NoticController.class);

    @Autowired
    private NoticService noticService;

    @GetMapping("/getByNoticById")
    public ResponseResult getByNoticById(@NotNull Long id){
        try {
            Assert.notNull(id,"id不能为空！");
            Notic notic = noticService.getByNoticById(id);
            return ResponseResult.success(notic);
        } catch (Exception e) {
            log.error("获取公告失败{}",e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }
    @GetMapping("/getByNoticAll")
    public ResponseResult getByNoticAll(){
        try {
            List<Notic> noticAll = noticService.getByNoticAll();
            return ResponseResult.success(noticAll);
        } catch (Exception e) {
            log.error("获取所有在线公告失败{}",e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    @GetMapping("/getByNoticList")
    public ResponseResult getByNoticList(){
        try {
            List<Notic> noticList = noticService.getByNoticList();
            return ResponseResult.success(noticList);
        } catch (Exception e) {
            log.error("获取公告所有列表失败{}",e.getMessage());
            return ResponseResult.fail(e.getMessage());        }
    }

    @GetMapping("/delete")
    public ResponseResult delete(@NotNull Long id){
        try {
          noticService.delete(id);
          return ResponseResult.success();
        } catch (Exception e) {
            log.error("删除公告失败{}",e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    @PostMapping("/saveOrUpdateNotic")
    public ResponseResult saveOrUpdateNotic(@RequestBody Notic notic){
        try {
            Assert.notNull(notic,"请传入notic!!!");
           noticService.saveOrUpdateNotic(notic);
            return ResponseResult.success();
        } catch (Exception e) {
            log.error("新增或修改公告失败{}",e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    @PostMapping("/updateStates")
    public ResponseResult updateStates(@RequestBody Notic notic) {
        try {
            Assert.notNull(notic.getId(),"id不能为空！！");
            noticService.updateStates(notic);
            return ResponseResult.success();
        } catch (Exception e) {
            log.error("更新公告状态失败={}",e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }


}
