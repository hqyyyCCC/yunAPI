package com.hqy.yunapiinterface.controller;



import com.hqy.yunapiclientsdk.model.entity.User;
import com.hqy.yunapiclientsdk.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名称API
 *
 * @author hqy
 */
@Slf4j
@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/{name1}")
    public String getNameByGet(@PathVariable String name1,String name,HttpServletRequest request){
        System.out.println("addHeader"+request.getHeader("Test-addRequest"));

        return "your name1 +name is --- "+name1+name;
    }
    @PostMapping
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request){

        // 鉴权已经在网关重新做过了
        /*//  以下逻辑校验应当交给服务端去校验
        // 校验 参数 来判定是否有权限
        // 1. assessKey 2. 随机数nonce  3. 用户参数 4. 时间戳 5.签名
        String assessKey = request.getHeader("assessKey");
        String nonce = request.getHeader("nonce");
        String body = request.getHeader("body");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        //todo 正常去校验数据库里是否有这个accessKey
        if(!assessKey.equals("yunapi")){
            log.info("assessKey校验失败");
            throw new RuntimeException("assessKey校验失败");
        }
        //todo  随机数校验 一般是去数据库中查看是否有该随机数，有则不通过(随机数只用一次)
        if(Long.parseLong(nonce)>10000){
            log.info("随机数校验失败");
            throw new RuntimeException("随机数校验失败");
        }
        //todo  时间戳校验 和当前时间不超过五分钟
        *//*if(!timestamp)){
            throw new RuntimeException("用户没有权限");
        }*//*

        //todo  签名校验 正常得去数据库获取secretKey
        String currentSign = SignUtil.getSign(body,"abcdefgh");

        if(!sign.equals(currentSign)){
            log.info("签名校验失败");
            throw new RuntimeException("签名校验失败");
        }*/

        String res =  "your name is "+user.getUsername();
        // 调用次数加一

        return res ;
    }
}
