package com.hqy.yunapiclientsdk.model.entity;

import com.hqy.yunapiclientsdk.exception.ErrorApiException;
import com.hqy.yunapiclientsdk.exception.ErrorCode;

import java.lang.reflect.Field;

public class test {
    public static void main(String[] args) throws ErrorApiException {
        WallPaper wallPaper = new WallPaper("pc", "dongman", "json");
        String lx = wallPaper.getLx();
        System.out.println(lx);
        Field[] fields = wallPaper.getClass().getDeclaredFields();
        StringBuffer url = new StringBuffer("https://btstu.cn/sjbz/api.php");
        Boolean isFirstParam = true;
        for(Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            // 跳过序列化versionUid
            if (name.equals("serialVersionUID")) {
                continue;
            }
            try {
                //根据字段名称获取对应Obj对应字段的值
                Object o = field.get(wallPaper);
                if (o != null) {
                    if (isFirstParam) {
                        url.append("?").append(name).append("=").append(o);
                        isFirstParam = false;
                    } else {
                        url.append("&").append(name).append("=").append(o);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new ErrorApiException(ErrorCode.PARAMS_ERROR, "构建Url失败");
            }
        }
        System.out.println(url);
    }
}
