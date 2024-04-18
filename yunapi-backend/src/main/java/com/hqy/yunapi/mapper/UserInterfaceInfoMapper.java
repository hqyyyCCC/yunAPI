package com.hqy.yunapi.mapper;

import com.hqy.yunapi.model.vo.InterfaceInfoVO;
import com.hqy.yunapicommon.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 原生优质青年
* @description 针对表【user_interface_info(接口调用信息表)】的数据库操作Mapper
* @createDate 2024-03-21 10:37:08
* @Entity com.hqy.yunapicommon.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
//select interfaceInfoId,sum(totalNum) as total
//    from user_interface_info
//    group by interfaceInfoId
//    order by total desc
//    limit 3;
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




