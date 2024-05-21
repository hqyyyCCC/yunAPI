package com.hqy.yunapiclientsdk.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WallPaper implements Serializable {
    /**
     * 	名称	    必填	    类型	    说明
     * 	method	否	    string	输出壁纸端[mobile|pc|zsy]默认为pc
 * 		lx	    否	    string	选择输出分类[meizi|dongman|fengjing|suiji]，为空随机输出
     * 	format	否	    string	输出壁纸格式[json|images]默认为images
     */
    private static final long serialVersionUID = 1L;
    private String method;
    private String lx;
    private String format;
 }
