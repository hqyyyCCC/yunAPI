package com.hqy.yunapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hqy.yunapi.common.ErrorCode;
import com.hqy.yunapi.exception.BusinessException;
import com.hqy.yunapicommon.model.entity.UserInterfaceInfo;
import com.hqy.yunapi.mapper.UserInterfaceInfoMapper;
import com.hqy.yunapi.service.UserInterfaceInfoService;
import com.hqy.yunapicommon.service.InnerUserInterfaceInfoService;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 原生优质青年
* @description 针对表【user_interface_info(接口调用信息表)】的数据库操作Service实现
* @createDate 2024-03-21 10:37:08
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;
    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        Long id = userInterfaceInfo.getId();
        Long userId = userInterfaceInfo.getUserId();
        Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
        Integer totalNum = userInterfaceInfo.getTotalNum();
        Integer leftNum = userInterfaceInfo.getLeftNum();
        Integer status = userInterfaceInfo.getStatus();
        Date createTime = userInterfaceInfo.getCreateTime();
        Date updateTime = userInterfaceInfo.getUpdateTime();
        Integer isDelete = userInterfaceInfo.getIsDelete();

        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 创建时，校验参数合法性
        if (add) {
            if(interfaceInfoId<=0 ||id<=0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        // 剩余次数校验
        if (leftNum<0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不足");
        }

    }
    // 统计top
    @Override
    public List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit) {
        return  userInterfaceInfoMapper.listTopInvokeInterfaceInfo(limit);
    }





    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId",interfaceInfoId);
        updateWrapper.eq("userId",userId);
        // 关于leftNum 还需要验证大于等于0
        updateWrapper.setSql("leftNum = leftNum-1 , totalNum = totalNum + 1 ");
        return  this.update(updateWrapper);
    }
}




