package com.hqy.yunapiclientsdk;

import com.hqy.yunapiclientsdk.client.YunApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("yunapi.client")
@Data
@ComponentScan
public class YunApiClientConfig {
    private String accessKey;
    private String secretKey;

    @Bean
    public YunApiClient yunApiClient(){
        return new YunApiClient(accessKey,secretKey);
    }
}
