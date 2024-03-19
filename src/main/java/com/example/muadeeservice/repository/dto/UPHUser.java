package com.example.muadeeservice.repository.dto;

import com.example.commonapi.model.AbstractTimestampEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "UPH_USER")
public class UPHUser extends AbstractTimestampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_REGISTER_SEQUENCE")
    @SequenceGenerator(name = "USER_REGISTER_SEQUENCE", sequenceName = "USER_REGISTER_SEQUENCE", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    @Column(name = "REFERENCE_ID")
    private String referenceId;
    @Column(name = "CARD_NO")
    private String cardNumber;

    @Column(name = "CARD_HOLDER_NAME")
    private String cardName;

    @Column(name = "CUSTOMER_ID")
    private String customerId;
    @Column(name = "ACCT_NO")
    private String accountNo;

    @Column(name = "ACCT_TYPE")
    private String accountType;

    @Column(name = "CIF")
    private String cif;
    @Column(name = "MERCHANT_ID")
    private String merchantId;

    @Column(name = "PHONE_NUMBER")
    private String mobileNo;

    @Column(name = "TOKEN_ID")
    private String tokenId;

    @Column(name = "BANK_USER")
    private String userId;
    @Column(name = "PARTNER_CODE")
    private String partnerCode;

    @Column(name = "STATUS")
    private Integer status;


}
