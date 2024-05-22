package com.hqy.yunapiclientsdk.model.response;

import lombok.Data;

@Data
public class PublicIpResponse {
    /**
     * code	string	200成功，400错误
     * msg	string	如果状态码返回400则返回错误信息提示。
     * zhou	string	大洲。
     * guo	string	国家。
     * sheng	string	省级名称。
     * shi	string	市级名称。
     * qu	string	区级名称。
     * isp	string	运营商名称。
     */
    private String code;
    private String msg;
    private String zhou;
    private String guo;
    private String sheng;
    private String shi;
    private String qu;
    private String isp;
}
