package com.hqy.yunapi.service.impl.inner;

import com.hqy.yunapi.service.UserInterfaceInfoService;
import com.hqy.yunapi.service.impl.UserInterfaceInfoServiceImpl;
import com.hqy.yunapicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoServiceImpl userInterfaceInfoServiceImpl;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoServiceImpl.invokeCount(interfaceInfoId,userId);
    }
}
