package com.example.muadeeservice.service;

import com.example.commongrpc.ecrm.VerifyUserResponse;

public interface EcrmClientService {

    VerifyUserResponse verifyUserEbank (String clientNo, String acctType, String partnerId);
}
