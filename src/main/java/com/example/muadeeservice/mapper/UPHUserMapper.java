package com.example.muadeeservice.mapper;

import com.example.commonapi.parameter.enumable.EUserStatus;
import com.example.commonapi.service.ReferenceService;
import com.example.commonapi.service.Utils;
import com.example.commongrpc.common.UserBanking;
import com.example.commongrpc.way4.CardInfoResponse;
import com.example.muadeeservice.model.InitRegister;
import com.example.muadeeservice.repository.dto.UPHMerchant;
import com.example.muadeeservice.repository.dto.UPHUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UPHUserMapper {
    @Autowired
    private ReferenceService referenceService;
    public UPHUser map(InitRegister initRegister, UPHMerchant uphMerchant, CardInfoResponse cardInfoResponse, UserBanking userEbank, String tokenId)
    {
        UPHUser uphUser = new UPHUser();
        uphUser.setReferenceId(referenceService.newReference());
        uphUser.setUserId(userEbank.getUsername());
        uphUser.setCardNumber(Utils.getMaskCardNo(initRegister.getCardRegister().getCardNo()));
        uphUser.setCardName(initRegister.getCardRegister().getCardHolderName());
        uphUser.setCustomerId(initRegister.getCardRegister().getCardNo().substring(6,12));
        uphUser.setAccountNo(cardInfoResponse.getExtras().getCBSNumber());
        uphUser.setAccountType(userEbank.getAcctType());
        uphUser.setCif(userEbank.getClientCode());
        uphUser.setMerchantId(uphMerchant.getMerchantId());
        uphUser.setMobileNo(cardInfoResponse.getExtras().getPhoneMobile());
        uphUser.setStatus(EUserStatus.AUTHORIZATION.getStatus());
        uphUser.setPartnerCode(initRegister.getPartnerCode());
        uphUser.setTokenId(tokenId);
        return uphUser;
    }

}
