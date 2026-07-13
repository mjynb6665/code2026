package com.example.entity;

import java.math.BigDecimal;
import java.util.List;

public class Order {

    /** 主键ID */
    private Integer id;
    /** 订单编号 */
    private String orderNo;
    /** 用户ID */
    private Integer userId;
    /** 用户名称 */
    private String userName;
    /** 总金额 */
    private BigDecimal total;
    /** 状态：待付款、已付款、已取消 */
    private String status;
    /** 创建时间 */
    private String time;
    /** 订单项列表（非数据库字段） */
    private List<OrderItem> items;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}