package com.hqy.yunapiclientsdk.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PublicIpParams implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ipAddress;
}
