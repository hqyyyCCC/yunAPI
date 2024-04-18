
use api;
-- 接口信息表
create table if not exists api.`user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '调用用户id',
    `interfaceInfoId` bigint not null comment '接口id',
    `totalNum` int default 0 not null comment '总调用次数',
    `leftNum` int default 0 not null comment '剩余调用次数',
    `status` tinyint default 0 not null comment '接口状态（0 - 正常，1-禁用）',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删,1-已删)'

) comment '接口调用信息表';

insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('gCij', '中国农业大学', 'www.keith-emmerich.net', '5dEx', 'nPD', 'at3s', 41337, '2022-12-03 22:01:21', '2022-04-10 17:46:58');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('5hP', '南大学', 'www.moshe-ebert.io', '281', 'Fh41', 'MEMA2', 63272522, '2022-10-25 10:06:10', '2022-05-24 21:00:31');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('hsrf', '东南科技大学', 'www.irving-roob.name', 'DHKa1', 'qx2', '35s', 2, '2022-01-08 17:05:09', '2022-08-25 13:33:44');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('2K', '西南技术大学', 'www.manda-cassin.io', 'Ml', 'HY2', 'ZOCD', 34199266, '2022-08-20 07:20:39', '2022-11-27 13:30:47');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('4zq', '中国体育大学', 'www.delores-bashirian.biz', 'Ph8', 'Xv', 'zoAzi', 56672, '2022-02-26 00:55:31', '2022-01-11 20:48:37');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('3ZYZT', '东北大学', 'www.drusilla-fadel.com', 'gxd', 'u2', 'Obka1', 49, '2022-02-20 14:41:32', '2022-07-23 23:37:57');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('FuBS', '东艺术大学', 'www.tanisha-christiansen.io', '9OJ', '09gyl', 'QQ4i6', 84375176, '2022-05-18 21:37:07', '2022-08-27 03:16:29');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('aI', '北科技大学', 'www.angelo-bruen.info', 'ecySv', '2sHu', '9zf', 765, '2022-04-10 16:33:16', '2022-05-14 05:53:12');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('zkskY', '西南科技大学', 'www.pamila-sanford.net', 'ypi', 'BLTYm', 'TAMD', 446726794, '2022-06-25 02:49:16', '2022-11-17 09:25:28');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('k1r', '东南理工大学', 'www.kristofer-daniel.org', 'FBj8', 'PaI', 'YAXz', 377, '2022-06-07 01:57:58', '2022-05-01 09:17:47');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('jN8ZL', '北农业大学', 'www.teodora-hackett.org', 'dhYQg', 'ZuTW3', '11', 90, '2022-06-17 00:34:48', '2022-03-20 07:14:32');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('uiyT1', '西南农业大学', 'www.allan-schiller.com', 'Yvs', 'ZEyt', 'AC', 234133212, '2022-09-01 18:43:54', '2022-03-03 23:34:48');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('79O', '西北理工大学', 'www.carissa-kutch.org', 'MpBUX', 'FDy', '2M', 496781893, '2022-06-28 03:25:17', '2022-12-10 21:27:11');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('OYZ', '西理工大学', 'www.miguel-pouros.co', '60f', 'ETx', 'GE', 37307688, '2022-11-18 04:12:31', '2022-02-06 07:48:25');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('Zm', '西经贸大学', 'www.araceli-auer.co', 'J3w', '2tK1', 'RKvl', 90533, '2022-03-26 05:15:54', '2022-03-21 17:11:47');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('aPCOL', '西北农业大学', 'www.tonie-rutherford.name', 'U7L1g', 'PY', 'HDD', 51, '2022-01-21 02:11:18', '2022-12-21 00:10:44');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('tA', '西南体育大学', 'www.valeri-mante.info', 'kpC4', '9E', 'rZbd', 30, '2022-03-14 03:47:42', '2022-08-05 17:06:35');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('vWaT', '东南艺术大学', 'www.marty-predovic.com', 'knn', 'gtU', 'SPAD9', 4251792557, '2022-05-30 21:40:24', '2022-05-30 03:23:10');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('B3D', '东北科技大学', 'www.gabriel-ferry.info', 'Sc', 'tyede', '7er', 528048994, '2022-10-31 09:13:38', '2022-09-23 04:23:16');
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `method`, `userId`, `createTime`, `updateTime`) values ('LJzJ', '北理工大学', 'www.aletha-schulist.info', 'Bp', 'bz', '07TK', 666365210, '2022-12-16 16:21:25', '2022-01-06 05:15:11');

##接口调用次数统计
select interfaceInfoId,sum(totalNum) as total
    from user_interface_info
    group by interfaceInfoId
    order by total desc
    limit 3;
select * from interface_info
where id in (2,22,5);