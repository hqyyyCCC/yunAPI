package com.hqy.yunapi.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;


/**
 * 更新请求
 *
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    private Long id;

    /**
     * 用户调用接口请求参数
     */
    private Map<String,Object> userRequestParams;




    private static final long serialVersionUID = 1L;
}