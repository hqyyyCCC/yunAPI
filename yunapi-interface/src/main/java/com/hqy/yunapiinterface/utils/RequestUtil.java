package com.hqy.yunapiinterface.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.hqy.yunapiclientsdk.exception.ErrorApiException;
import com.hqy.yunapiclientsdk.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
@Slf4j
public class RequestUtil {
    /**
     * 构造Get请求方法的Url
     * @param baseUrl
     * @param params
     * @return
     * @param <T>
     */
    public static <T> String buildUrl(String baseUrl,T params) throws ErrorApiException {
        StringBuilder url = new StringBuilder(baseUrl);
        Field[] fields = params.getClass().getFields();
        Boolean isFirstParam = true;
        for(Field field : fields){
            field.setAccessible(true);
            String name =field.getName();
            // 跳过序列化versionUid
            if(name.equals("serialVersionUID")){
                continue;
            }
            try {
                //根据字段名称获取对应Obj对应字段的值
                Object o = field.get(params);
                if(o!=null){
                    if(isFirstParam){
                        url.append("?").append(name).append("=").append(o);
                        isFirstParam=false;
                    }else{
                        url.append("&").append(name).append("=").append(o);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new ErrorApiException(ErrorCode.PARAMS_ERROR,"构建Url失败");
            }
        }
        return url.toString();
    }
    public static <T> String get(String baseurl,T params) throws ErrorApiException {
        String url ;
        if(params!=null){
            url = buildUrl(baseurl, params);
        }else{
            url = baseurl;
        }
        String body = HttpRequest.get(url)
                .execute()
                .body();
        log.info("【interface】：请求地址：{}，响应数据：{}", url, body);
        return body;
    }


}
