package com.hqy.yunapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hqy.yunapi.common.ErrorCode;
import com.hqy.yunapi.exception.BusinessException;
import com.hqy.yunapi.mapper.UserMapper;
import com.hqy.yunapicommon.model.entity.User;
import com.hqy.yunapicommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User getInvokeUser(String assessKey) {
        if(StringUtils.isAnyBlank(assessKey)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("assessKey",assessKey);

        return userMapper.selectOne(queryWrapper);
    }
}
