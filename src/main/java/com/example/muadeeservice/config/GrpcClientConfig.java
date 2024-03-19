package com.example.muadeeservice.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {
    @Autowired
    private GrpcConfig grpcConfig;

    @Bean("configManagerCoreClient")
    public ManagedChannel configManagerCoreClient() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(String.valueOf(grpcConfig.getCore().get("host")), (Integer) grpcConfig.getCore().get("port"))
                .usePlaintext()
                .build();
        return managedChannel;
    }
    @Bean("configManagerWay4Client")
    public ManagedChannel configManagerWay4Client() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(String.valueOf(grpcConfig.getWay4().get("host")), (Integer) grpcConfig.getWay4().get("port"))
                .usePlaintext()
                .build();
        return managedChannel;
    }
    @Bean("configManagerECrmClient")
    public ManagedChannel configManagerECrmClient() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(String.valueOf(grpcConfig.getEcrm().get("host")), (Integer) grpcConfig.getEcrm().get("port"))
                .usePlaintext()
                .build();
        return managedChannel;
    }
}
