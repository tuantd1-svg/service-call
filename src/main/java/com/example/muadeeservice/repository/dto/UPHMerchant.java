package com.example.muadeeservice.repository.dto;

import com.example.commonapi.parameter.enumable.EMerchantStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "UPH_MERCHANT_CONFIG")
public class UPHMerchant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UPH_MERCHANT_CONFIG_SEQUENCE")
    @SequenceGenerator(name = "UPH_MERCHANT_CONFIG_SEQUENCE", sequenceName = "UPH_MERCHANT_CONFIG_SEQUENCE", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    @Column(name = "MERCHANT_ID")
    private String merchantId;

    @Column(name = "MERCHANT_NAME")
    private String merchantName;

    @Column(name = "BIT_17")
    private String bit17;

    @Column(name = "BIT_32")
    private String bit32;

    @Column(name = "BIT_41")
    private String bit41;

    @Column(name = "ACCOUNT_MERCHANT")
    private String accountNo;

    @Column(name = "IS_REVERSAL")
    private boolean isReversal;

    @Column(name = "ACTIVE")
    @Enumerated(EnumType.STRING)
    private EMerchantStatus status;


}
