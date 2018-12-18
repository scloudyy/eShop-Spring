# the sql for each tables

```sql
CREATE TABLE `tb_area` (
  `area_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `area_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `priority` int(2) unsigned NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `UK_AREA` (`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_head_line` (
  `line_id` int(100) unsigned NOT NULL AUTO_INCREMENT,
  `line_name` varchar(1000) DEFAULT NULL,
  `line_link` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `line_img` varchar(2000) NOT NULL DEFAULT '',
  `priority` int(5) DEFAULT NULL,
  `enable_status` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_person_info` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `profile_img` varchar(1024) DEFAULT NULL,
  `email` varchar(1024) DEFAULT NULL,
  `gender` varchar(8) DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '0: not allowed, 1: allowed',
  `user_type` int(2) NOT NULL DEFAULT '1' COMMENT '1: custom, 2:seller, 3:admin',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_local_auth` (
  `local_auth_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `username` varchar(128) NOT NULL DEFAULT '',
  `password` varchar(128) NOT NULL DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`local_auth_id`),
  UNIQUE KEY `UK_LOCAL_PROFILE` (`username`),
  KEY `FK_LOCALAUTH_PROFILE` (`user_id`),
  CONSTRAINT `FK_LOCALAUTH_PROFILE` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

REATE TABLE `tb_wechat_auth` (
  `wechat_auth_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `open_id` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`wechat_auth_id`),
  UNIQUE KEY `UK_OPEN_ID` (`open_id`),
  KEY `FK_WECHATAUTH_PROFILE` (`user_id`),
  CONSTRAINT `FK_WECHATAUTH_PROFILE` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_shop_category` (
  `shop_categoty_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `shop_categoty_name` varchar(100) NOT NULL DEFAULT '''''',
  `shop_categoty_desc` varchar(1000) DEFAULT '''''',
  `priority` int(3) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `parent_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`shop_categoty_id`),
  KEY `FK_SHOP_CATEGORY_SELF` (`parent_id`),
  CONSTRAINT `FK_SHOP_CATEGORY_SELF` FOREIGN KEY (`parent_id`) REFERENCES `tb_shop_category` (`shop_categoty_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_shop` (
  `shop_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `owner_id` int(10) unsigned NOT NULL,
  `area_id` int(10) unsigned DEFAULT NULL,
  `shop_category_id` int(11) unsigned DEFAULT NULL,
  `shop_name` varchar(256) DEFAULT NULL,
  `shop_desc` varchar(1024) DEFAULT NULL,
  `shop_addr` varchar(200) DEFAULT NULL,
  `phone` varchar(128) DEFAULT NULL,
  `shop_img` varchar(1024) DEFAULT NULL,
  `priority` int(3) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) DEFAULT '0' COMMENT '-1: not allowed, 0: check, 1: allowed',
  `advice` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`shop_id`),
  KEY `FK_SHOP_AREA` (`area_id`),
  KEY `FK_SHOP_PROFILE` (`owner_id`),
  KEY `FK_SHOPCATE` (`shop_category_id`),
  CONSTRAINT `FK_SHOPCATE` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_categoty_id`),
  CONSTRAINT `FK_SHOP_AREA` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`),
  CONSTRAINT `FK_SHOP_PROFILE` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_product_categoty` (
  `product_categoty_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `product_categoty_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `shop_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`product_categoty_id`),
  KEY `FK_PROCATE_SHOP` (`shop_id`),
  CONSTRAINT `FK_PROCATE_SHOP` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_product` (
  `product_id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `product_desc` varchar(2000) DEFAULT NULL,
  `img_addr` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '''''',
  `normal_price` varchar(100) DEFAULT NULL,
  `promotion_price` varchar(100) DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `product_categoty_id` int(11) unsigned DEFAULT NULL,
  `shop_id` int(10) unsigned DEFAULT '0',
  `point` int(8) DEFAULT '0',
  PRIMARY KEY (`product_id`),
  KEY `FK_PRODUCT_PROCATE` (`product_categoty_id`),
  KEY `FK_PRODUCT_SHOP` (`shop_id`),
  CONSTRAINT `FK_PRODUCT_PROCATE` FOREIGN KEY (`product_categoty_id`) REFERENCES `tb_product_categoty` (`product_categoty_id`),
  CONSTRAINT `FK_PRODUCT_SHOP` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_product_img` (
  `product_img_id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `img_addr` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `img_desc` varchar(2000) DEFAULT NULL,
  `priority` int(2) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `product_id` int(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`product_img_id`),
  KEY `FK_PROIMG_PRODUCT` (`product_id`),
  CONSTRAINT `FK_PROIMG_PRODUCT` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```