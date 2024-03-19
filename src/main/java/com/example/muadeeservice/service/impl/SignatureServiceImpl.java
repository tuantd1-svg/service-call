package com.example.muadeeservice.service.impl;

import com.example.commongrpc.coreeoc.CoreEOCServiceGrpc;
import com.example.commongrpc.signature.SignatureServiceGrpc;
import com.example.commongrpc.signature.VerifySignatureRequest;
import com.example.commongrpc.signature.VerifySignatureResponse;
import com.example.muadeeservice.service.SignatureGrpcClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class SignatureServiceImpl implements SignatureGrpcClient {
    @GrpcClient("SignatureService")
    private SignatureServiceGrpc.SignatureServiceBlockingStub signatureServiceBlockingStub;

    @Override
    public VerifySignatureResponse verifySignature(VerifySignatureRequest request) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9099)
                .usePlaintext()
                .build();
        signatureServiceBlockingStub = SignatureServiceGrpc.newBlockingStub(channel);
        VerifySignatureResponse verifySignatureResponse = signatureServiceBlockingStub.verifySignature(request);
        channel.shutdown();
        return verifySignatureResponse;
    }
}
