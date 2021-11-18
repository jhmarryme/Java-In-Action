CREATE
DATABASE /*!32312 IF NOT EXISTS*/`distribute` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE
`distribute`;
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `id`              int(11) NOT NULL AUTO_INCREMENT,
    `order_status`    int(1) NOT NULL,
    `receiver_name`   varchar(255)   NOT NULL,
    `receiver_mobile` varchar(11)    NOT NULL,
    `order_amount`    decimal(11, 0) NOT NULL,
    `create_time`     time           NOT NULL,
    `create_user`     varchar(255)   NOT NULL,
    `update_time`     time           NOT NULL,
    `update_user`     varchar(255)   NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `order_id`       int(11) NOT NULL,
    `product_id`     int(11) NOT NULL,
    `purchase_price` decimal(11, 0) NOT NULL,
    `purchase_num`   int(3) NOT NULL,
    `create_time`    time           NOT NULL,
    `create_user`    varchar(255)   NOT NULL,
    `update_time`    time           NOT NULL,
    `update_user`    varchar(255)   NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `product_name` varchar(255)   NOT NULL COMMENT '商品名称',
    `price`        decimal(11, 0) NOT NULL COMMENT '价格',
    `count`        int(5) NOT NULL COMMENT '库存',
    `product_desc` varchar(255)   NOT NULL COMMENT '描述',
    `create_time`  time           NOT NULL COMMENT '创建时间',
    `create_user`  varchar(255)   NOT NULL COMMENT '创建人',
    `update_time`  time           NOT NULL COMMENT '更新时间',
    `update_user`  varchar(255)   NOT NULL COMMENT '更新人',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100101 DEFAULT CHARSET=utf8mb4;
insert into `product`(`id`, `product_name`, `price`, `count`, `product_desc`, `create_time`, `create_user`,
                      `update_time`, `update_user`)
values (100100, '测试商品', '1', 1, '测试商品', '18:06:00', '周红', '19:19:21',
        'xxx');/**后续分布式锁需要用到**/DROP TABLE IF EXISTS `distribute_lock`;
CREATE TABLE `distribute_lock`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `business_code` varchar(255) NOT NULL COMMENT '根据业务代码区分，不同业务使用不同锁',
    `business_name` varchar(255) NOT NULL COMMENT '注释，标记编码用途',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
insert into `distribute_lock`(`id`, `business_code`, `business_name`)
values (1, 'demo', 'test');



delete from order;
delete from order_item;
update product set count = 1;