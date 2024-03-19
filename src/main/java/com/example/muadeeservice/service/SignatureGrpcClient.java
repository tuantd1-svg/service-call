package com.example.muadeeservice.service;

import com.example.commongrpc.signature.VerifySignatureRequest;
import com.example.commongrpc.signature.VerifySignatureResponse;

public interface SignatureGrpcClient {
    VerifySignatureResponse verifySignature(VerifySignatureRequest request);
}
