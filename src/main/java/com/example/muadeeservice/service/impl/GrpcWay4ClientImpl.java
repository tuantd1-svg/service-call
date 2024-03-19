package com.example.muadeeservice.service.impl;

import com.example.commongrpc.way4.CardInfoRequest;
import com.example.commongrpc.way4.CardInfoResponse;
import com.example.commongrpc.way4.Way4ServiceGrpc;
import com.example.muadeeservice.config.GrpcConfig;
import com.example.muadeeservice.service.GrpcWay4Client;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GrpcWay4ClientImpl implements GrpcWay4Client {

    @Autowired
    private GrpcConfig grpcConfig;
    @GrpcClient("Way4Service")
    private Way4ServiceGrpc.Way4ServiceBlockingStub way4ServiceBlockingStub;

    @Autowired
    @Qualifier("configManagerWay4Client")
    private ManagedChannel managedChannelCore;
    @Override
    public CardInfoResponse getCardInfoResponse(String requestId, String cardNumber) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(String.valueOf(grpcConfig.getWay4().get("host")), (Integer) grpcConfig.getWay4().get("port"))
                .usePlaintext()
                .build();
        Way4ServiceGrpc.Way4ServiceBlockingStub stub
                = Way4ServiceGrpc.newBlockingStub(managedChannel);
        CardInfoResponse cardInfoResponse = stub.getInfoWay4(CardInfoRequest.newBuilder().setRequestId(requestId).setContractNumber(cardNumber).build());
        managedChannel.shutdown();
        return cardInfoResponse;
    }
}
