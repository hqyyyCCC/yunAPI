package com.hqy.yunapiclientsdk.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
}
