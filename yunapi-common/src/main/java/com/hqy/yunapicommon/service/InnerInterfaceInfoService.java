package com.hqy.yunapicommon.service;


import com.hqy.yunapicommon.model.entity.InterfaceInfo;


/**
* @author 原生优质青年
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2024-03-13 16:08:57
*/
public interface InnerInterfaceInfoService {

    InterfaceInfo getInterfaceInfo(String  url , String method);


}
