CREATE TABLE `bid_user` (
  `user_num` varchar(16) NOT NULL,
  `remain_count` int(11) DEFAULT NULL,
  `pw` varchar(45) DEFAULT NULL,
  `modify_date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_num`)
);