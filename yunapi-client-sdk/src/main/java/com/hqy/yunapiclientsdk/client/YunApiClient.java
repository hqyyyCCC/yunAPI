package com.hqy.yunapiclientsdk.client;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.hqy.yunapiclientsdk.exception.ErrorApiException;
import com.hqy.yunapiclientsdk.exception.ErrorCode;
import com.hqy.yunapiclientsdk.model.BaseRequest;
import com.hqy.yunapiclientsdk.model.entity.User;
import com.hqy.yunapiclientsdk.model.enums.UrlToMethodEnum;
import com.hqy.yunapiclientsdk.model.response.UserResponse;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.hqy.yunapiclientsdk.utils.SignUtil.getSign;
@Slf4j
public class YunApiClient {
    private String  assessKey;
    private String  secretKey;
    // 网关地址
    private static final String GATEWAY_URL = "http://localhost:8090" ;
//    private static final String HOST = "http://8.149.128.43:8090" ;
//    private static final String HOST = "http://47.113.120.231:8090" ;
    public YunApiClient(String assessKey, String secretKey) {
        this.assessKey = assessKey;
        this.secretKey = secretKey;
    }



    /**
     * @param body 用户参数
     * @return 返回完整的header 发出请求
     */
    public Map<String ,String > getHeaderMap(String body){
        Map<String,String> hashMap  = new HashMap<>();
        // 1. assessKey 2. 随机数  3. 用户参数 4. 时间戳 5.签名
        hashMap.put("assessKey",assessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body",body);
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        hashMap.put("sign", getSign(body,secretKey));

        return hashMap;
    }

    /**
     *
     * @param user 用户
     * @return 返回用户名
     */
    public UserResponse getUserNameByPost(User user) throws ErrorApiException {
        return sendRequest(GATEWAY_URL+ UrlToMethodEnum.NAME.getPath(),"POST", user, UserResponse.class);
    }

    /**
     * 根据url来解析方法
     * @param baseRequest
     * @return
     */
    public Object parseAddressAndInvokeInterface(BaseRequest baseRequest) throws ErrorApiException {
       /* private String path;
        private String method;
        private Map<String,Object> params;
        private HttpServletRequest httpServletRequest;*/
        //获取所有的Enum对象
        String path = baseRequest.getPath();
        String method = baseRequest.getMethod();
        Map<String,Object> paramsList = baseRequest.getParams();
        HttpServletRequest httpServletRequest = baseRequest.getUserRequest();
        Object res = null ;

        Set<String> paths = UrlToMethodEnum.getPaths();

//        collect.contains(path)
        log.info("请求地址:{},请求方法:{},请求参数:{}",path,method,paramsList);
        if(paths.contains(path)){
            if(path.equals(UrlToMethodEnum.NAME.getPath())){

                res = invokeMethod(UrlToMethodEnum.NAME.getMethod(),paramsList,User.class);
            }
        }else{
            throw new ErrorApiException(ErrorCode.PARAMS_ERROR,"请求地址有误");
        }
        if(ObjUtil.isEmpty(res)){
            throw new ErrorApiException(ErrorCode.NOT_FOUND_ERROR,"未找到Url对应资源");
        }
        log.info("返回结果:{}",res);
        return res;
    }

    private Object invokeMethod(String methodName,Map<String,Object> params,Class<?> paramsType) throws ErrorApiException {
        try {
            Class<?> clazz = YunApiClient.class;
            if(params == null){
                Method method = clazz.getMethod(methodName);
                return method.invoke(this);
            }else{
                log.info("接收到的参数 params:{} paramsType:{}", params, paramsType);
                // String -> Object 通过new Gson.fromJsonStr(String,Object.class)
                // Map -> Bean  通过BeanUtil.mapToBean

                Method method = clazz.getMethod(methodName, paramsType);
                //将参数转为Bean 再执行方法
                Object paramsObject = BeanUtil.mapToBean(params, paramsType,true, CopyOptions.create());
                return method.invoke(paramsObject,paramsObject);
            }
        } catch (NoSuchMethodException e) {
            throw new ErrorApiException(ErrorCode.NOT_FOUND_ERROR, "通过url未找到对应方法");
        } catch (Exception e){
            //处理剩下异常
            if(e.getCause() instanceof ErrorApiException){
                ErrorApiException cause = (ErrorApiException) e.getCause();
                throw  new ErrorApiException(cause.getCode(),cause.getMessage());
            }
            throw  new ErrorApiException(ErrorCode.NOT_FOUND_ERROR,e.getMessage());
        }

    }

    /**
     * 发送请求
     */
    public <T> T sendRequest(String url,String method,Object params,Class<T> responseType)throws ErrorApiException {
        HttpRequest httpRequest;
        String jsonStr = JSONUtil.toJsonStr(params);
        switch (method.toUpperCase()){
            case "GET":
                log.info("params:{}",params);
                httpRequest = HttpRequest.get(url);
            case "POST":
                log.info("封装post请求body:{}",jsonStr);
                httpRequest=HttpRequest.post(url);
                handleParamsAsBody(httpRequest,jsonStr);
                break;
            default:
                throw new ErrorApiException(ErrorCode.MEHTOD_NOT_ALLOWED,"请求方式有误");
        }
        //添加请求头
        httpRequest.addHeaders(getHeaderMap(jsonStr));
        //发送请求获取响应
        HttpResponse response = httpRequest.execute();
        //获取响应体
        String responseBody = response.body();
        //处理网关返回异常
        if(response.getStatus()!=200){
            ErrorCode errorCode = JSONUtil.toBean(responseBody, ErrorCode.class);
            throw new ErrorApiException(errorCode.getCode(),errorCode.getMessage());
        }

        return JSON.parseObject(responseBody,responseType);
    }

    /**
     * 对http请求处理
     * @param request
     * @param body
     */
    public void handleParamsAsBody(HttpRequest request,String body){
        if(body!= null){
            request.header("Content-Type", "application/json; charset=UTF-8").body(body);
        }
    }

}
