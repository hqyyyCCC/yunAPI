package com.hqy.yunapiclientsdk.model.enums;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public enum UrlToMethodEnum {
    NAME("/api/name","getUserNameByPost"),
    PoisonousChickenSoup("/api/poisonousChickenSoup","getPoisonousChickenSoup"),

    RandomWallPaper("/api/randomWallPaper","getRandomWallPaper"),
    PublicIp("/api/publicIp", "getPublicIp");
    private final String path;
    private final String method;

    UrlToMethodEnum(String path, String method) {
        this.path = path;
        this.method = method;
    }

    /**
     * 获取所有的路径
     * @return
     */
    public static Set<String> getPaths(){
        UrlToMethodEnum[] values = values();
        Set<String> paths =new HashSet<>();
        Stream.of(values).forEach(v->{
            paths.add(v.getPath());
        });
        return paths;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }
}
