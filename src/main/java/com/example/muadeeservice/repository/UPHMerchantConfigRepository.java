package com.example.muadeeservice.repository;

import com.example.muadeeservice.repository.dto.UPHMerchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UPHMerchantConfigRepository extends JpaRepository<UPHMerchant,Long> {
    UPHMerchant findUPHMerchantByMerchantId(String merchantId);
}
