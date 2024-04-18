package com.hqy.yunapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hqy.yunapicommon.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
