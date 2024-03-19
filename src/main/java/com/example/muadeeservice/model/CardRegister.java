package com.example.muadeeservice.model;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Data
@Getter
public class CardRegister {
    @NotNull
    private String cardNo;
    @NotNull
    private String cardHolderName;
    @NotNull
    private String openDate;

    @NotNull
    private String merchantId;
}
