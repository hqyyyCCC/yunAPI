package com.hqy.yunapi.gateway;

import com.hqy.yunapiclientsdk.utils.SignUtil;
import com.hqy.yunapicommon.model.entity.InterfaceInfo;
import com.hqy.yunapicommon.model.entity.User;
import com.hqy.yunapicommon.service.InnerInterfaceInfoService;
import com.hqy.yunapicommon.service.InnerUserInterfaceInfoService;
import com.hqy.yunapicommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @DubboReference
    private InnerUserService userService;
    @DubboReference
    private InnerUserInterfaceInfoService userInterfaceInfoService;
    @DubboReference
    private InnerInterfaceInfoService interfaceInfoService;

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1","172.28.53.217","172.28.247.22");
    private static final String INTERFACE_HOST = "http://localhost:8123";
//    private static final String INTERFACE_HOST = "http://8.149.128.43:8123";
//    private static final String INTERFACE_HOST = "http://47.113.120.231:8123";


    private static final long FIVE_MINUTES = 60*5 ;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("进入全局过滤器");
        //1. 用户发送请求到API网关
        //2. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String path = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + path);
        log.info("请求方法：" + method);
        log.info("请求参数：" + request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址：" + sourceAddress);
        log.info("请求去向地址：" + request.getRemoteAddress());


        ServerHttpResponse response = exchange.getResponse();


        //3. 黑白名单(此处就是封禁IP处理)
        if(!IP_WHITE_LIST.contains(sourceAddress)){
            // 返回FORBIDDEN
            return handleNoAuth(response);
        };

        //4. 用户鉴权(判断ak以及sk是否合法（并非是用户是否有权限）)
        // 校验 参数 来判定是否有权限
        // 1. assessKey 2. 随机数nonce  3. 用户参数 4. 时间戳 5.签名
        HttpHeaders headers = request.getHeaders();
        String assessKey = headers.getFirst("assessKey");
        String nonce = headers.getFirst("nonce");
        String body = headers.getFirst("body");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        //todo 正常去校验数据库里是否有这个accessKey
        User invokeUser = null;
        try{
            invokeUser= userService.getInvokeUser(assessKey);
        }catch (Exception e){
            log.info("assessKey校验失败");
        }
        if(invokeUser == null){
            return handleNoAuth(response);
        }


        //todo  随机数校验 一般是去数据库中查看是否有该随机数，有则不通过(随机数只用一次)
        if(Long.parseLong(nonce)>10000){
            log.info("随机数校验失败");
            return handleNoAuth(response);
        }


        //todo  时间戳校验 和当前时间不超过五分钟
        if(Long.parseLong(timestamp)-System.currentTimeMillis()/1000 >= FIVE_MINUTES){
            log.info("时间戳校验失败");
            return handleNoAuth(response);
        }

        //done  签名校验 正常得去数据库获取secretKey
        String secretKey = invokeUser.getSecretKey();
        String currentSign = SignUtil.getSign(body,secretKey);
        if(sign == null || !sign.equals(currentSign)){
            log.info("签名校验失败");
            return handleNoAuth(response);
        }
        //5. 请求的模拟接口是否存在
        //todo (在backend项目的数据库interface_info 表看是否有存在对应的接口信息以及请求方法是否能够匹配)
        InterfaceInfo interfaceInfo = null;
        try{
            interfaceInfo=interfaceInfoService.getInterfaceInfo(path,method);
        }catch (Exception e){
            log.error("获取接口信息失败 getInterfaceInfo error", e);
        }
        if(interfaceInfo ==  null){
            return handleNoAuth(response);
        }


        //6. 请求转发，调用模拟接口
//        Mono<Void> filter = chain.filter(exchange);

        //7. 响应日志
        return handleResponse(exchange,chain,interfaceInfo.getId(),invokeUser.getId());
//        log.info("status"+response.getStatusCode());
//        //todo 8. 调用成功，调用接口次数+1
//        if(response.getStatusCode() == HttpStatus.OK){
//
//        }else{
//        //9. 调用失败，返回一个规范的错误码
//            log.info("调用接口失败");
//            return handleInvokeError(response);
//        }
//
//        return filter;
    }
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId){
        try {
            //从交换寄拿响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            //缓冲区工厂，拿到缓存数据
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            //拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if(statusCode == HttpStatus.OK){
                //装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    //等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        //对象是响应式的
                        if (body instanceof Flux) {
                            //我们拿到真正的body
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            //往返回值里面写数据
                            //拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        // 8. 调用成功， todo 接口调用次数+1 invokeCount
                                        try{
                                            userInterfaceInfoService.invokeCount(interfaceInfoId,userId);
                                        }catch (Exception e){
                                            log.error("invokeCount error", e);
                                        }

                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8);//data
                                        sb2.append(data);
                                        //打印日志
                                        log.info("响应结果" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            // 9. 调用失败，返回规范错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                //设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        }catch (Exception e){
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }
    @Override
    public int getOrder() {
        return -1;
    }
    public Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
    public Mono<Void> handleInvokeError(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}