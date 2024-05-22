package com.hqy.yunapiinterface.controller;



import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.hqy.yunapiclientsdk.exception.ErrorApiException;
import com.hqy.yunapiclientsdk.model.entity.PublicIpParams;
import com.hqy.yunapiclientsdk.model.entity.User;
import com.hqy.yunapiclientsdk.model.entity.WallPaper;
import com.hqy.yunapiclientsdk.model.response.*;
import com.hqy.yunapiinterface.utils.RequestUtil;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;

/**
 * 名称API
 * @author hqy
 */
@Slf4j
@RestController
public class InterfaceInfoController {
    @GetMapping("/{name1}")
    public String getNameByGet(@PathVariable String name1,String name,HttpServletRequest request){
        System.out.println("addHeader"+request.getHeader("Test-addRequest"));

        return "your name1 +name is --- "+name1+name;
    }
    @PostMapping("/name")
    public UserResponse getUserNameByPost(@RequestBody User user, HttpServletRequest request){

        UserResponse userResponse = BeanUtil.copyProperties(user, UserResponse.class);
        // 调用次数加一
        log.info("访问成功 userResponse:{}" , userResponse);
        return userResponse ;
    }
    @GetMapping("/poisonousChickenSoup")
    public PoisonousChickenSoupResponse getPoisonousChickenSoup() throws ErrorApiException {
        String responseBody = RequestUtil.get("https://api.btstu.cn/yan/api.php?charset=utf-8&encode=json",null);
        // json -》 obj
        return JSON.parseObject(responseBody, PoisonousChickenSoupResponse.class);
    }
    @GetMapping("/randomWallPaper")
    public RandomWallPaperResponse getRandomWallPaper(@RequestParam String lx,@RequestParam String format) throws ErrorApiException{

        String responseBody = RequestUtil.get("https://btstu.cn/sjbz/api.php",new WallPaper(null,lx,format));
        return JSON.parseObject(responseBody,RandomWallPaperResponse.class);
    }
    @GetMapping("/publicIp")
    public PublicIpResponse PublicIp(@RequestParam String ip) throws ErrorApiException {
        String responseBody = RequestUtil.get("https://cn.apihz.cn/api/ip/chaapi.php",new PublicIpParams(ip));
        return JSON.parseObject(responseBody,PublicIpResponse.class);
    }

}
