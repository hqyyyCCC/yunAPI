package com.hqy.yunapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hqy.yunapi.annotation.AuthCheck;
import com.hqy.yunapi.common.BaseResponse;
import com.hqy.yunapi.common.ErrorCode;
import com.hqy.yunapi.common.ResultUtils;
import com.hqy.yunapi.exception.BusinessException;
import com.hqy.yunapi.model.vo.InterfaceInfoVO;
import com.hqy.yunapi.service.InterfaceInfoService;
import com.hqy.yunapi.service.UserInterfaceInfoService;
import com.hqy.yunapicommon.model.entity.InterfaceInfo;
import com.hqy.yunapicommon.model.entity.UserInterfaceInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Resource
    private InterfaceInfoService interfaceInfoService;
    private static final Integer LIST_LIMIT = 3;

    @GetMapping("top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo(){
        // 查询接口调用次数top级别的接口信息
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoService.listTopInvokeInterfaceInfo(LIST_LIMIT);
        //将接口信息按照接口id分组
        Map<Long, List<UserInterfaceInfo>> interfaceInfoObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        //创建查询的条件包装器
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",interfaceInfoObjMap.keySet());

       /* //通过传入条件包装器 得到符合条件的信息IsService 的list方法 获取符合条件的接口信息
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);

        //判断查询结果是否为空
        if(CollectionUtils.isEmpty(list)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //构建VO列表，将接口信息映射为接口VO对象并加入列表中
        List<InterfaceInfoVO> collect = list.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            int totalNum=interfaceInfoObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        collect.forEach(System.out::println);

        //返回结果
        return ResultUtils.success(collect);*/
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);

            int totalNum = interfaceInfoObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            System.out.println(interfaceInfoVO);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(interfaceInfoVOList);

    }
}
