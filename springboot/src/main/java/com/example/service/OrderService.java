package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Cart;
import com.example.entity.Goods;
import com.example.entity.Order;
import com.example.entity.OrderItem;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.CartMapper;
import com.example.mapper.GoodsMapper;
import com.example.mapper.OrderMapper;
import com.example.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private CartMapper cartMapper;

    /**
     * 从购物车创建订单
     */
    @Transactional
    public Order createOrderFromCart(Integer userId, List<Integer> cartIds) {
        User user = userMapper.selectById(userId);
        if (ObjectUtil.isNull(user)) {
            throw new CustomException("用户不存在");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Integer cartId : cartIds) {
            Cart cart = cartMapper.selectById(cartId);
            if (ObjectUtil.isNull(cart)) {
                throw new CustomException("购物车商品不存在");
            }
            Goods goods = goodsMapper.selectById(cart.getGoodsId());
            if (ObjectUtil.isNull(goods)) {
                throw new CustomException("商品不存在");
            }
            if ("下架".equals(goods.getStatus())) {
                throw new CustomException("商品「" + goods.getName() + "」已下架");
            }
            if (goods.getStore() == null || goods.getStore() < cart.getNum()) {
                throw new CustomException("商品「" + goods.getName() + "」库存不足");
            }

            OrderItem item = new OrderItem();
            item.setGoodsId(goods.getId());
            item.setGoodsName(goods.getName());
            item.setGoodsImg(goods.getImg());
            item.setGoodsPrice(goods.getPrice());
            item.setNum(cart.getNum());
            orderItems.add(item);

            total = total.add(goods.getPrice().multiply(new BigDecimal(cart.getNum())));
        }

        if (user.getAccount() == null || user.getAccount().compareTo(total) < 0) {
            throw new CustomException("账户余额不足");
        }

        Order order = new Order();
        order.setOrderNo(IdUtil.fastSimpleUUID().substring(0, 16));
        order.setUserId(userId);
        order.setTotal(total);
        order.setStatus("已付款");
        order.setTime(DateUtil.now());
        orderMapper.insertOrder(order);

        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem item = orderItems.get(i);
            item.setOrderId(order.getId());
            orderMapper.insertOrderItem(item);

            Integer cartId = cartIds.get(i);
            Cart cart = cartMapper.selectById(cartId);
            Goods goods = goodsMapper.selectById(cart.getGoodsId());
            goods.setStore(goods.getStore() - cart.getNum());
            goods.setSaleCount((goods.getSaleCount() == null ? 0 : goods.getSaleCount()) + cart.getNum());
            goodsMapper.updateById(goods);

            cartMapper.deleteById(cartId);
        }

        user.setAccount(user.getAccount().subtract(total));
        userMapper.updateByid(user);

        return order;
    }

    /**
     * 直接购买（立即购买）
     */
    @Transactional
    public Order createOrderDirect(Integer userId, Integer goodsId, Integer num) {
        User user = userMapper.selectById(userId);
        if (ObjectUtil.isNull(user)) {
            throw new CustomException("用户不存在");
        }

        Goods goods = goodsMapper.selectById(goodsId);
        if (ObjectUtil.isNull(goods)) {
            throw new CustomException("商品不存在");
        }
        if ("下架".equals(goods.getStatus())) {
            throw new CustomException("商品已下架");
        }
        if (goods.getStore() == null || goods.getStore() < num) {
            throw new CustomException("库存不足");
        }

        BigDecimal total = goods.getPrice().multiply(new BigDecimal(num));

        if (user.getAccount() == null || user.getAccount().compareTo(total) < 0) {
            throw new CustomException("账户余额不足");
        }

        Order order = new Order();
        order.setOrderNo(IdUtil.fastSimpleUUID().substring(0, 16));
        order.setUserId(userId);
        order.setTotal(total);
        order.setStatus("已付款");
        order.setTime(DateUtil.now());
        orderMapper.insertOrder(order);

        OrderItem item = new OrderItem();
        item.setOrderId(order.getId());
        item.setGoodsId(goods.getId());
        item.setGoodsName(goods.getName());
        item.setGoodsImg(goods.getImg());
        item.setGoodsPrice(goods.getPrice());
        item.setNum(num);
        orderMapper.insertOrderItem(item);

        goods.setStore(goods.getStore() - num);
        goods.setSaleCount((goods.getSaleCount() == null ? 0 : goods.getSaleCount()) + num);
        goodsMapper.updateById(goods);

        user.setAccount(user.getAccount().subtract(total));
        userMapper.updateByid(user);

        return order;
    }

    
    /**
     * 更新订单状态
     */
    public void updateStatus(Integer id, String status) {
        Order order = new Order();
        order.setId(id);
        order.setStatus(status);
        orderMapper.updateById(order);
    }

    public void deleteById(Integer id) {
        orderMapper.deleteItemsByOrderId(id);
        orderMapper.deleteById(id);
    }

    public Order selectById(Integer id) {
        Order order = orderMapper.selectById(id);
        order.setItems(orderMapper.selectItemsByOrderId(id));
        return order;
    }

    public List<Order> selectAll(Order order) {
        List<Order> orders = orderMapper.selectAll(order);
        for (Order o : orders) {
            o.setItems(orderMapper.selectItemsByOrderId(o.getId()));
        }
        return orders;
    }

    public PageInfo<Order> selectPage(Order order, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> list = selectAll(order);
        return PageInfo.of(list);
    }
}