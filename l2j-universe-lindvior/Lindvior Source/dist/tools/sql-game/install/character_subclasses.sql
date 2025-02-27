CREATE TABLE `character_subclasses` (
  `char_obj_id` int(11) NOT NULL,
  `class_id` tinyint(3) unsigned NOT NULL,
  `default_class_id` tinyint(3) unsigned NOT NULL,
  `level` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `exp` bigint(20) unsigned NOT NULL DEFAULT '0',
  `sp` bigint(20) unsigned NOT NULL DEFAULT '0',
  `curHp` decimal(15,4) unsigned NOT NULL DEFAULT '0.0000',
  `curMp` decimal(15,4) unsigned NOT NULL DEFAULT '0.0000',
  `curCp` decimal(15,4) unsigned NOT NULL DEFAULT '0.0000',
  `maxHp` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `maxMp` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `maxCp` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `active` tinyint(1) NOT NULL DEFAULT '0',
  `type` VARCHAR(45) NOT NULL DEFAULT '',
  `death_penalty` tinyint(4) NOT NULL DEFAULT '0',
  `certification` int(11) NOT NULL,
  PRIMARY KEY (`char_obj_id`,`class_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;