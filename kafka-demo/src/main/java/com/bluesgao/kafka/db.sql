CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_no` varchar(128) NOT NULL COMMENT '用户号',
  `name` varchar(128) NOT NULL COMMENT '名称',
  `addr` varchar(128) NOT NULL COMMENT '名称',
  `c_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `m_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_userno` (`user_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户信息表(宽表)';

## 数据库实例 demo
CREATE TABLE `t_user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_no` varchar(128) NOT NULL COMMENT '用户号',
  `name` varchar(128) NOT NULL COMMENT '名称',
  `c_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `m_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_userno` (`user_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户信息表';


CREATE TABLE `t_user_addr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_no` varchar(128) NOT NULL COMMENT '用户号',
  `addr` varchar(128) NOT NULL COMMENT '地址',
  `c_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `m_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户地址表';



select * from t_user;



## 切换数据库
use user;

DELIMITER //
DROP PROCEDURE IF EXISTS `auto_gen_record`;
CREATE PROCEDURE `auto_gen_record`(in n INT)
  BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE u_no LONG DEFAULT 0;
    WHILE i < n DO
      SET u_no = UUID_SHORT();
      insert t_user(user_no,name,addr) value (u_no,CONCAT("name",u_no),CONCAT("addr",u_no));
      SET i = i + 1;
    END WHILE;
  END //

SELECT DISTINCT CONCAT('User: ''',user,'''@''',host,''';') AS query FROM mysql.user;

show grants for 'admin'@'%';
select * from mysql.user where user in('admin','root');



## 查看存储过程状态
SHOW PROCEDURE STATUS [LIKE auto_gen_record]

## --还是声明分隔符 --若存储过程存在，则删除
## --创建存储过程，参数为插入数据条数n
DELIMITER //
DROP PROCEDURE IF EXISTS `auto_gen_record`;
CREATE PROCEDURE `auto_gen_record`(in n INT)
  BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE u_no LONG DEFAULT 0;
    WHILE i < n DO
      SET u_no = UUID_SHORT();
      insert t_user(user_no,name,addr) value (u_no,CONCAT("name",u_no),CONCAT("addr",u_no));
      SET i = i + 1;
    END WHILE;
  END //

CALL auto_gen_record(10);

CALL auto_gen_record(10);

show create table t_user;
show create table t_user_addr;
show create table t_user_info;
show index from t_user;
show index from t_user_addr;
show index from t_user_info;

## 手工调用存储过程
CALL auto_gen_record(10);

#### mysql 事件 ######################################################################
## MySQL事件一般配合存储过程使用，创建存储过程后由MySQL事件进行触发调用
-- 开启事件调度器
SET GLOBAL event_scheduler = ON;
-- 关闭事件调度器
SET GLOBAL event_scheduler = OFF;
-- 查看事件调度器状态
SHOW VARIABLES LIKE 'event_scheduler';

-- 查看事件
SELECT event_name,event_definition,interval_value,interval_field,status FROM information_schema.EVENTS;

-- 删除事件
DROP EVENT IF EXISTS 事件名;

-- 开启事件
alter event 事件名 on completion preserve enable; -- 开启定时任务

-- 关闭事件
alter event 事件名 on completion preserve disable; -- 关闭定时任务

-- 创建事件
create event auto_gen_record_event on schedule every 3 second do call auto_gen_record(1);
-- 参数解释
create event second_event       -- 创建名为 second_event 的事件,注意此处没有括号
  on schedule every 1 second      -- 创建周期定时的规则，每秒钟执行一次
  on completion preserve disable  -- 创建后并不开始生效
do call test_proce();           -- do call test_proce() 是该事件的操作内容，表示调用名为 test_proce() 的存储过程。


