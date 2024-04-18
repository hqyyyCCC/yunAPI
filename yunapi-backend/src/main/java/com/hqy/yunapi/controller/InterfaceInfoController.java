package com.hqy.yunapi.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hqy.yunapi.annotation.AuthCheck;
import com.hqy.yunapi.common.*;
import com.hqy.yunapi.constant.CommonConstant;
import com.hqy.yunapi.exception.BusinessException;
import com.hqy.yunapi.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.hqy.yunapi.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.hqy.yunapi.model.dto.interfaceInfo.InterfaceInfoUpdateRequest;
import com.hqy.yunapi.model.dto.interfaceInfo.InterfaceInfoInvokeRequest;
import com.hqy.yunapiclientsdk.exception.ErrorApiException;
import com.hqy.yunapiclientsdk.model.BaseRequest;
import com.hqy.yunapicommon.model.entity.InterfaceInfo;
import com.hqy.yunapicommon.model.entity.User;
import com.hqy.yunapi.model.enums.InterfaceInfoStatusEnum;
import com.hqy.yunapi.service.InterfaceInfoService;
import com.hqy.yunapi.service.UserService;
import com.hqy.yunapiclientsdk.client.YunApiClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 接口信息
 *
 * @author hqy
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private YunApiClient yunApiClient;

    // interfaceInfo 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        // 校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param interfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                            HttpServletRequest request) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String description = interfaceInfoQuery.getDescription();
        // description 需支持模糊搜索
        interfaceInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);

    }



    /**
     * 发布接口 1. 校验接口是否已经存在 2.判断接口是否能调用 3.修改数据库的状态字段status表示能接口的上线状态
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                     HttpServletRequest request) {
        // 1. 校验接口是否存在
        if(idRequest == null || idRequest.getId() < 0){
            throw  new  BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断是否数据库中是否存在
        long id = idRequest.getId();
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 判断接口是否能调用
        // todo 判断接口是否能调用时由固定方法名改为根据测试地址来调用
        com.hqy.yunapiclientsdk.model.entity.User  user = new com.hqy.yunapiclientsdk.model.entity.User();
        user.setUsername("test");

        URL url;
        try {
            url = new URL(oldInterfaceInfo.getUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String path = url.getPath();
        String method = oldInterfaceInfo.getMethod();

        // String -> Map
        Object res ;
        try {
            res = yunApiClient.parseAddressAndInvokeInterface(new BaseRequest(path,method,null,request));
        } catch (ErrorApiException e) {
            throw new BusinessException(e.getCode(),e.getMessage());
        }
        if (res == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "接口验证失败");
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());

        boolean updateResult = interfaceInfoService.updateById(interfaceInfo);
        
        return ResultUtils.success(updateResult);
    }
    /**
     * 下线接口 1.校验接口是否存在 2.修改数据库status字段为0
     *
     * @param idRequest 请求的id
     * @param request 请求
     * @return
     */
    @PostMapping("/offline")
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                      HttpServletRequest request) {
        // 1. 校验接口是否存在
        if(idRequest == null || idRequest.getId() < 0){
            throw  new  BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断是否数据库中是否存在
        long id = idRequest.getId();
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }



        // 仅本人或管理员可修改
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        System.out.println("offline:"+InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest invokeRequest,
                                                      HttpServletRequest request) {
        String remoteHost = request.getPathInfo();

        // 1. 校验接口是否存在
        if(invokeRequest == null || invokeRequest.getId() < 0){
            throw  new  BusinessException(ErrorCode.PARAMS_ERROR,"接口不存在");
        }
        long id = invokeRequest.getId();
        Map<String,Object> userRequestParams = invokeRequest.getUserRequestParams();

        // 获取数据库的对应接口信息
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);

        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口信息不存在");
        }
        if (oldInterfaceInfo.getStatus() == InterfaceInfoStatusEnum.OFFLINE.getValue()  ) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口已关闭");
        }

        User loginUser  = userService.getLoginUser(request);
        String assessKey = loginUser.getAssessKey();
        String secretKey = loginUser.getSecretKey();
        YunApiClient tempYunApiClient = new YunApiClient(assessKey,secretKey);
        // String Json -》  实体对象  new Gson().fromJson(）
        // Gson gson = new Gson();
        URL url ;
        try {
            url = new URL(oldInterfaceInfo.getUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String path = url.getPath();
        String method = oldInterfaceInfo.getMethod();

        // String -> Map
        Object res ;
        try {
            res = tempYunApiClient.parseAddressAndInvokeInterface(new BaseRequest(path,method,userRequestParams,request));
        } catch (ErrorApiException e) {
            throw new BusinessException(e.getCode(),e.getMessage());
        }
        if(ObjUtil.isEmpty(res)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求SDK失败");
        }
        log.info ("调用SDK返回结果:{}",res);

        return ResultUtils.success(res);
    }
}
