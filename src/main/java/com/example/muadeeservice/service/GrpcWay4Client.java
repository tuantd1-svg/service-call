package com.example.muadeeservice.service;


import com.example.commongrpc.way4.CardInfoResponse;

public interface GrpcWay4Client {
    CardInfoResponse getCardInfoResponse(String requestId,String cardNumber);
}
