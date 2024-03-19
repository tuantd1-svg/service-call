package com.example.muadeeservice.repository;

import com.example.muadeeservice.repository.dto.UPHUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UPHUserRepository extends JpaRepository<UPHUser,Long> {
    UPHUser findUPHUserByCustomerIdAndMerchantIdAndPartnerCode(String customerId, String merchantId, String partnerCode);

    UPHUser findDistinctByCustomerIdAndMerchantIdAndPartnerCode(String customerId, String merchantId, String partnerCode);

    UPHUser findFirstByCustomerIdAndMerchantIdAndPartnerCode(String customerId, String merchantId, String partnerCode);
}
