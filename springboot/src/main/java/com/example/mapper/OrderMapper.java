package com.example.mapper;

import com.example.entity.Order;
import com.example.entity.OrderItem;

import java.util.List;

public interface OrderMapper {

    int insertOrder(Order order);

    int insertOrderItem(OrderItem orderItem);

    int deleteById(Integer id);

    int deleteItemsByOrderId(Integer orderId);

    int updateById(Order order);

    Order selectById(Integer id);

    List<OrderItem> selectItemsByOrderId(Integer orderId);

    List<Order> selectAll(Order order);
}