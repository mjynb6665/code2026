package com.example.controller;

import com.example.common.Result;
import com.example.service.StatService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stat")
public class StatController {

    @Resource
    private StatService statService;

    /** 销售概览 */
    @GetMapping("/overview")
    public Result overview() {
        Map<String, Object> data = statService.overview();
        return Result.success(data);
    }

    /** 销售趋势 — type=day 按天 / type=month 按月，默认近7天/近12月 */
    @GetMapping("/trend")
    public Result trend(@RequestParam(defaultValue = "day") String type,
                        @RequestParam(defaultValue = "7") int count) {
        List<Map<String, Object>> list;
        if ("month".equals(type)) {
            list = statService.monthlyTrend(count);
        } else {
            list = statService.dailyTrend(count);
        }
        return Result.success(list);
    }

    /** 分类销售占比 */
    @GetMapping("/category")
    public Result categorySales() {
        List<Map<String, Object>> list = statService.categorySales();
        return Result.success(list);
    }

    /** 商品销量 TOP N */
    @GetMapping("/topGoods")
    public Result topGoods(@RequestParam(defaultValue = "10") int limit) {
        List<Map<String, Object>> list = statService.topGoods(limit);
        return Result.success(list);
    }
}
