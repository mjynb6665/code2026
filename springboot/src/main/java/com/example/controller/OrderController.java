package com.example.controller;

import com.example.common.Result;
import com.example.entity.Order;
import com.example.service.OrderService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/createFromCart")
    public Result createFromCart(@RequestBody Map<String, Object> params) {
        Integer userId = (Integer) params.get("userId");
        @SuppressWarnings("unchecked")
        List<Integer> cartIds = (List<Integer>) params.get("cartIds");
        if (cartIds == null || cartIds.isEmpty()) {
            return Result.error("请选择要购买的商品");
        }
        Order order = orderService.createOrderFromCart(userId, cartIds);
        return Result.success(order);
    }

    @PostMapping("/createDirect")
    public Result createDirect(@RequestBody Map<String, Object> params) {
        Integer userId = (Integer) params.get("userId");
        Integer goodsId = (Integer) params.get("goodsId");
        Integer num = (Integer) params.get("num");
        if (num == null || num < 1) {
            num = 1;
        }
        Order order = orderService.createOrderDirect(userId, goodsId, num);
        return Result.success(order);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        orderService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Order order = orderService.selectById(id);
        return Result.success(order);
    }

    @GetMapping("/selectAll")
    public Result selectAll(Order order) {
        List<Order> list = orderService.selectAll(order);
        return Result.success(list);
    }

    @GetMapping("/selectPage")
    public Result selectPage(Order order,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Order> page = orderService.selectPage(order, pageNum, pageSize);
        return Result.success(page);
    }
}