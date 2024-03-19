package com.example.muadeeservice.repository.dto;

import com.example.commonapi.model.AbstractTimestampEntity;
import com.example.commonapi.parameter.enumable.ECurrency;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "UPH_TRANSACTION")
public class UPHTransaction extends AbstractTimestampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UPH_TRANSACTION_SEQUENCE")
    @SequenceGenerator(name = "UPH_TRANSACTION_SEQUENCE", sequenceName = "UPH_TRANSACTION_SEQUENCE", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "REQUEST_ID")
    private String requestId;

    @Column(name = "REFERENCE_ID")
    private String referenceId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UPHUser UPHUser;

    @Column(name = "ACCOUNT_NO")
    private String acctNo;
    @Column(name = "CREDIT_ACCOUNT")
    private String creditAccountNo;

    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY")
    private ECurrency currency;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "VERIFY_DATE")
    private String verifyDate;

    @Column(name = "ERROR_CODE")
    private String errorCode;

    @Column(name = "ERROR_MESSAGE")
    private String errorMessage;

}
