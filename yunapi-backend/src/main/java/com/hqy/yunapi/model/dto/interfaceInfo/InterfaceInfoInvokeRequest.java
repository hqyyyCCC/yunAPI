package com.hqy.yunapi.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;


/**
 * 更新请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
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