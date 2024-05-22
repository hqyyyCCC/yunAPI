package com.hqy.yunapiclientsdk.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicIpParams implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String id ="88888888";
    private final String key ="88888888";
    private String ip;
}
