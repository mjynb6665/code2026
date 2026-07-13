-- 订单表
CREATE TABLE IF NOT EXISTS `order` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `order_no` VARCHAR(32) NOT NULL COMMENT '订单编号',
    `user_id` INT NOT NULL COMMENT '用户ID',
    `total` DECIMAL(10,2) NOT NULL COMMENT '总金额',
    `status` VARCHAR(20) NOT NULL DEFAULT '已付款' COMMENT '状态：待付款、已付款、已取消',
    `time` VARCHAR(20) NOT NULL COMMENT '创建时间',
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 订单项表
CREATE TABLE IF NOT EXISTS `order_item` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `order_id` INT NOT NULL COMMENT '订单ID',
    `goods_id` INT NOT NULL COMMENT '商品ID',
    `goods_name` VARCHAR(255) COMMENT '商品名称',
    `goods_img` VARCHAR(500) COMMENT '商品图片',
    `goods_price` DECIMAL(10,2) COMMENT '商品单价',
    `num` INT NOT NULL COMMENT '数量',
    KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单项表';