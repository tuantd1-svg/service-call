package com.example.muadeeservice.service.impl;

import com.example.commonapi.model.ResultMessage;
import com.example.commonapi.parameter.enumable.EMerchantStatus;
import com.example.commonapi.parameter.enumable.EMessage;
import com.example.commonapi.parameter.enumable.EUserStatus;
import com.example.commonapi.parameter.enumable.ErrorCode;
import com.example.commonapi.service.ReferenceService;
import com.example.commonapi.service.Utils;
import com.example.commongrpc.common.Extras;
import com.example.commongrpc.coreeoc.VerifyAcctNoResponse;
import com.example.commongrpc.ecrm.VerifyUserResponse;
import com.example.commongrpc.way4.CardInfoResponse;
import com.example.muadeeservice.enumable.ErrorCodeCore;
import com.example.muadeeservice.mapper.UPHUserMapper;
import com.example.muadeeservice.model.InitRegister;
import com.example.muadeeservice.model.TokenResponse;
import com.example.muadeeservice.repository.UPHMerchantConfigRepository;
import com.example.muadeeservice.repository.UPHUserRepository;
import com.example.muadeeservice.repository.dto.UPHMerchant;
import com.example.muadeeservice.repository.dto.UPHUser;
import com.example.muadeeservice.service.EcrmClientService;
import com.example.muadeeservice.service.GrpcCoreClient;
import com.example.muadeeservice.service.GrpcWay4Client;
import com.example.muadeeservice.service.UPHService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UPHServiceImpl implements UPHService {
    @Autowired
    private UPHMerchantConfigRepository UPHMerchantConfigRepository;
    @Autowired
    private UPHUserRepository uphUserRepository;

    @Autowired
    private GrpcCoreClient grpcCoreClient;

    @Autowired
    private GrpcWay4Client grpcWay4Client;

    @Autowired
    private EcrmClientService ecrmClientService;

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private UPHUserMapper uphUserMapper;

    @Override
    public ResultMessage<TokenResponse> initRegister(InitRegister initRegister) {
        //begin checkMerchantExist
        UPHMerchant UPHMerchant = UPHMerchantConfigRepository.findUPHMerchantByMerchantId(initRegister.getCardRegister().getMerchantId());
        if (UPHMerchant == null)
            return new ResultMessage<>(ErrorCode.MERCHANT_NOT_FOUND.getCode(), ErrorCode.MERCHANT_NOT_FOUND.getMessage(), false, EMessage.INTERNAL_ERROR);
        if (EMerchantStatus.DISABLE.equals(UPHMerchant.getStatus()))
            return new ResultMessage<>(ErrorCode.MERCHANT_IS_DISABLE.getCode(), ErrorCode.MERCHANT_IS_DISABLE.getMessage(), false, EMessage.INTERNAL_ERROR);
        //end check
        //begin check link exist
        UPHUser uphUser = uphUserRepository.findUPHUserByCustomerIdAndMerchantIdAndPartnerCode(initRegister.getCardRegister().getCardNo().substring(6, 12), initRegister.getCardRegister().getMerchantId(), initRegister.getPartnerCode());
        if (uphUser != null && uphUser.getStatus().equals(EUserStatus.ACTIVE.getStatus()))
            return new ResultMessage<>(ErrorCode.USER_EXIST.getCode(), ErrorCode.USER_EXIST.getMessage(), false, EMessage.INTERNAL_ERROR);
        //end check
        //checkCardNo
        if (!Utils.checkLuhn(initRegister.getCardRegister().getCardNo())) {
            return new ResultMessage<>(ErrorCode.INVALID_CARD_NO.getCode(), ErrorCode.INVALID_CARD_NO.getMessage(), false, EMessage.INTERNAL_ERROR);
        }
        CardInfoResponse cardInfoResponse = null;
        if (initRegister.getCardRegister().getCardNo().startsWith("9704")) {
            if (!"970437".equals(initRegister.getCardRegister().getCardNo().substring(0, 6))) {
                return new ResultMessage<>(ErrorCode.INVALID_CARD_LOCAL_DEBIT.getCode(), ErrorCode.INVALID_CARD_LOCAL_DEBIT.getMessage(), false, EMessage.INTERNAL_ERROR);
            }
            cardInfoResponse = grpcWay4Client.getCardInfoResponse(initRegister.getRequestId(), initRegister.getCardRegister().getCardNo());
            if (!cardInfoResponse.hasExtras()) {
                return new ResultMessage<>(Integer.valueOf(cardInfoResponse.getResponseCode()), cardInfoResponse.getResponseMessage(), false, EMessage.INTERNAL_ERROR);
            }
            ErrorCode errorCode = verifyCard(initRegister.getCardRegister().getOpenDate(), initRegister.getCardRegister().getCardNo(), initRegister.getCardRegister().getCardHolderName(), cardInfoResponse);
            if (!ErrorCode.SUCCESS.equals(errorCode))
                return new ResultMessage<>(errorCode.getCode(), errorCode.getMessage(), false, EMessage.INTERNAL_ERROR);

        }
        //end check
        //validate eoc
        VerifyUserResponse verifyUserResponse;
        VerifyAcctNoResponse verifyAcctNoResponse = grpcCoreClient.verifyAcctNo(cardInfoResponse.getExtras().getCBSNumber(), initRegister.getCardRegister().getCardHolderName(), initRegister.getPartnerCode());
        if (!ErrorCodeCore.SUCCESS.getCode().equals(verifyAcctNoResponse.getErrorCode()))
            return new ResultMessage<>(Integer.valueOf(verifyAcctNoResponse.getErrorCode()), ErrorCodeCore.map(verifyAcctNoResponse.getErrorCode()).getMessage(), false, EMessage.INTERNAL_ERROR);
        try {
            verifyUserResponse = ecrmClientService.verifyUserEbank(verifyAcctNoResponse.getClientCode(), verifyAcctNoResponse.getAcctType(), initRegister.getPartnerCode());
            if (!verifyUserResponse.hasUserBanking())
                return new ResultMessage<>(Integer.valueOf(verifyUserResponse.getErrorCode()), verifyUserResponse.getErrorMessage(), false, EMessage.INTERNAL_ERROR);
        } catch (Exception e) {
            return new ResultMessage<>(ErrorCode.FAILURE.getCode(), ErrorCode.FAILURE.getMessage(), false, EMessage.INTERNAL_ERROR);
        }
        //generateToken
        String token = referenceService.generateToken();
        if (uphUser == null) {
            uphUser = uphUserRepository.save(uphUserMapper.map(initRegister, UPHMerchant, cardInfoResponse, verifyUserResponse.getUserBanking(), token));
        } else {
            uphUser.setTokenId(token);
            uphUserRepository.save(uphUser);
        }
        if (uphUser == null) {
            return new ResultMessage<>(ErrorCode.FAILURE.getCode(), ErrorCode.FAILURE.getMessage(), false, EMessage.INTERNAL_ERROR);
        }
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(uphUser.getTokenId());
        return new ResultMessage<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), true, EMessage.EXECUTE, tokenResponse);
    }

    @Override
    public ErrorCode verifyCard(String date, String cardNo, String holderName, CardInfoResponse cardInfoResponse) {

        ErrorCode errorCode = ErrorCode.SUCCESS;
        Extras extras = cardInfoResponse.getExtras();
        String openDateCore = Utils.getOpenDate(extras.getOpenDate());
        if (date.equals(openDateCore) || date.equals(extras.getExpirationDate())) errorCode = ErrorCode.SUCCESS;
        else
            return ErrorCode.DATE_TIME_INPUT_CARD_INVALID;
        if (StringUtils.isEmpty(extras.getContractName()))
            return ErrorCode.CARD_NAME_INVALID;
        String cardName = extras.getEmbossedFirstName().concat(" "+extras.getEmbossedLastName());
        if (!holderName.equals(cardName))
            return ErrorCode.CARD_NAME_INVALID;
        if (StringUtils.isEmpty(extras.getProductionStatus()))
            return ErrorCode.CARD_DISABLE;
        if (!"R".equals(extras.getProductionStatus().split(";")[0]))
            return ErrorCode.CARD_DISABLE;
        //check ready
        if (!extras.getReady().contains("Yes"))
            return ErrorCode.USER_NOT_ACTIVE;
        //end check
        //check main Card
        if (StringUtils.isEmpty(extras.getProductCode()))
            return ErrorCode.CARD_DISABLE;
        String[] productCode = extras.getProductCode().split("_");
        String lastPrefix = productCode[productCode.length - 1];
        if (!"M".equals(lastPrefix))
            return ErrorCode.CARD_DISABLE;
        //check card Status
        if (!"00".equals(extras.getCardStatus()))
            return ErrorCode.USER_NOT_ACTIVE;
        //end check
        return errorCode;
    }


}
