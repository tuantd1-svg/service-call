package com.example.muadeeservice.service.impl;

import com.example.commongrpc.ecrm.ECRMServiceGrpc;
import com.example.commongrpc.ecrm.VerifyUserRequest;
import com.example.commongrpc.ecrm.VerifyUserResponse;
import com.example.muadeeservice.config.GrpcConfig;
import com.example.muadeeservice.service.EcrmClientService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EcrmClientServiceImpl implements EcrmClientService {


    @GrpcClient("ecrmClient")
    private  ECRMServiceGrpc.ECRMServiceBlockingStub ecrmServiceBlockingStub;

    @Autowired
    private GrpcConfig grpcConfig;
    @Override
    public VerifyUserResponse verifyUserEbank(String clientNo, String acctType, String partnerId)
    {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(String.valueOf(grpcConfig.getEcrm().get("host")), (Integer) grpcConfig.getEcrm().get("port"))
                .usePlaintext()
                .build();
        ecrmServiceBlockingStub = ECRMServiceGrpc.newBlockingStub(managedChannel);
        VerifyUserRequest verifyUser= VerifyUserRequest.newBuilder().setClientNo(clientNo).setAcctType(acctType).setPartnerId(partnerId).build();
        VerifyUserResponse verifyUserResponse = ecrmServiceBlockingStub.verifyUser(verifyUser);
        managedChannel.shutdown();
        return verifyUserResponse;
    }
}
