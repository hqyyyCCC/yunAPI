package com.hqy.yunapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hqy.yunapi.common.ErrorCode;
import com.hqy.yunapi.exception.BusinessException;
import com.hqy.yunapi.mapper.InterfaceInfoMapper;
import com.hqy.yunapicommon.model.entity.InterfaceInfo;
import com.hqy.yunapicommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     *  根据URl和method判断接口信息是否存在并获取接口信息
     * @param url
     * @param method
     * @return
     */
    @Override
    public InterfaceInfo getInterfaceInfo(String  url, String  method) {
        if(StringUtils.isAnyBlank(url,method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",url);
        queryWrapper.eq("method",method);



        return  interfaceInfoMapper.selectOne(queryWrapper);
    }

}
