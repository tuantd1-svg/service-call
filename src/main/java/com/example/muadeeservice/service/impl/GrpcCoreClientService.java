package com.example.muadeeservice.service.impl;

import com.example.commongrpc.coreeoc.CoreEOCServiceGrpc;
import com.example.commongrpc.coreeoc.VerifyAcctNoRequest;
import com.example.commongrpc.coreeoc.VerifyAcctNoResponse;
import com.example.muadeeservice.config.GrpcConfig;
import com.example.muadeeservice.service.GrpcCoreClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GrpcCoreClientService implements GrpcCoreClient {

    @GrpcClient("CoreEOCService")
    private CoreEOCServiceGrpc.CoreEOCServiceBlockingStub coreEOCServiceBlockingStub;

    @Autowired
    private GrpcConfig grpcConfig;

    @Override
    public VerifyAcctNoResponse verifyAcctNo(String acctNo, String holderName, String partnerId) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(String.valueOf(grpcConfig.getCore().get("host")), (Integer) grpcConfig.getCore().get("port"))
                .usePlaintext()
                .build();
        coreEOCServiceBlockingStub = CoreEOCServiceGrpc.newBlockingStub(managedChannel);
        VerifyAcctNoRequest verifyAcctNoRequest = VerifyAcctNoRequest.newBuilder().setAcctNo(acctNo).setOwnerName(holderName).setPartnerId(partnerId).build();
        VerifyAcctNoResponse verifyAcctNoResponse = coreEOCServiceBlockingStub.verifyAcctNo(verifyAcctNoRequest);
        managedChannel.shutdown();
        return verifyAcctNoResponse;
    }
}
