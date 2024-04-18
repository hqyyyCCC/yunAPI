package com.hqy.yunapicommon.service;

import com.hqy.yunapicommon.model.entity.UserInterfaceInfo;


/**
* @author 原生优质青年
* @description 针对表【user_interface_info(接口调用信息表)】的数据库操作Service
* @createDate 2024-03-21 10:37:08
*/
public interface InnerUserInterfaceInfoService  {

    boolean invokeCount(long interfaceInfoId, long userId);
}
