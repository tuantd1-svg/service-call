package com.example.muadeeservice.service;

import com.example.commonapi.model.ResultMessage;
import com.example.commonapi.parameter.enumable.ErrorCode;
import com.example.commongrpc.way4.CardInfoResponse;
import com.example.muadeeservice.model.InitRegister;
import com.example.muadeeservice.model.TokenResponse;
import com.example.muadeeservice.model.UserEbank;

import java.sql.SQLException;

public interface UPHService {
    ResultMessage<TokenResponse> initRegister(InitRegister initRegister) throws SQLException;
    ErrorCode verifyCard(String date,String cardNo, String holderName ,CardInfoResponse cardInfoResponse);

}
