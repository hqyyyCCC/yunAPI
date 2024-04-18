package com.hqy.yunapi.model.vo;

import com.hqy.yunapi.model.entity.Post;
import com.hqy.yunapicommon.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 帖子视图
 *
 * @author hqy
 * @TableName product
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO extends InterfaceInfo {

    private  Integer totalNum;

    private static final long serialVersionUID = 1L;
}