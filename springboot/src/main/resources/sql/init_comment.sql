-- 评论表
CREATE TABLE IF NOT EXISTS `comment` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `goods_id` INT NOT NULL COMMENT '商品ID',
    `user_id` INT NOT NULL COMMENT '用户ID',
    `content` TEXT COMMENT '评论内容',
    `rating` TINYINT NOT NULL DEFAULT 5 COMMENT '评分（1-5星）',
    `time` DATETIME NOT NULL COMMENT '评论时间',
    KEY `idx_goods_id` (`goods_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品评论表';