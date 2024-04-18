package com.hqy.yunapicommon.service;



import com.hqy.yunapicommon.model.entity.User;


/**
 * 用户服务
 *
 * @author hqy
 */
public interface InnerUserService{
    /**
     * 实现在数据库中查询是否分配给用户assessKey
     * @param asseccKey 
     * @return
     */
    User getInvokeUser(String asseccKey);
}
