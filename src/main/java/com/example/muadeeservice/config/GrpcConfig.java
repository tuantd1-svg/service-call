package com.example.muadeeservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "grpc.client")
public class GrpcConfig {

    private Map<String, Object> core;

    private Map<String, Object> way4;

    private Map<String, Object> ecrm;

    public Map<String, Object> getCore() {
        return core;
    }

    public void setCore(Map<String, Object> core) {
        this.core = core;
    }

    public Map<String, Object> getWay4() {
        return way4;
    }

    public void setWay4(Map<String, Object> way4) {
        this.way4 = way4;
    }

    public Map<String, Object> getEcrm() {
        return ecrm;
    }

    public void setEcrm(Map<String, Object> ecrm) {
        this.ecrm = ecrm;
    }
}
