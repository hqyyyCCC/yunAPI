package com.hqy.yunapi.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 帖子性别枚举
 *
 * @author hqy
 */
public enum InterfaceInfoStatusEnum {
// 接口上线下线状态枚举
    ONLINE("上线", 1),
    OFFLINE("下线", 0);

    private final String description;

    private final int value;

    InterfaceInfoStatusEnum(String description, int value) {
        this.description = description;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return description;
    }
}
