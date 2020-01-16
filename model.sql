CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `gender` varchar(1) COLLATE utf8_spanish_ci DEFAULT NULL,
  `address` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `birthdate` varchar(10) COLLATE utf8_spanish_ci DEFAULT NULL,
  `nacionality` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL,
  `corporate` int(1) DEFAULT '0' COMMENT 'flag corporativo',
  `role` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci

CREATE TABLE `flight` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `origin` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `destination` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `departure` datetime NOT NULL,
  `arrival` datetime NOT NULL,
  `price` double NOT NULL,
  `currency` varchar(3) COLLATE utf8_spanish_ci NOT NULL,
  `offer` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci

CREATE TABLE `booking` (
  `userid` bigint(20) NOT NULL,
  `flightid` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `fk_user` (`userid`),
  KEY `fk_flight` (`flightid`),
  CONSTRAINT `fk_flight` FOREIGN KEY (`flightid`) REFERENCES `flight` (`id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci