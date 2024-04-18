package com.hqy.yunapiclientsdk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRequest {
    private String path;
    private String method;
    private Map<String,Object> params;
    private HttpServletRequest userRequest;
}
