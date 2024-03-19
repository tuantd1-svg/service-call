package com.example.muadeeservice.service;

import com.example.commongrpc.coreeoc.VerifyAcctNoResponse;

public interface GrpcCoreClient {
    VerifyAcctNoResponse verifyAcctNo(String acctNo,String holderName,String partnerId);
}
