package com.hqy.yunapicommon.model.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口信息表
 * @TableName interface_info
 */
@TableName(value ="interface_info")
@Data
public class InterfaceInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址 http:localhost:8123/user/name
     */
    private String url;
    /**
     * 请求参数
     *
     *  [
     *   {
     *     "name": "username",
     *     "type": "String"
     *   }
     * ]
     *
     */
    private String requestParams;
    /**
     * 请求头
     * {
     *   "Content-Type": "application/json"
     * }
     */
    private String requestHeader;

    /**
     * 响应头
     * {
     *   "Content-Type": "application/json"
     * }
     */
    private String responseHeader;

    /**
     * 接口状态（0 - 关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删,1-已删)
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}