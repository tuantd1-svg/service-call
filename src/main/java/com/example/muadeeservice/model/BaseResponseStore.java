package com.example.muadeeservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseStore {
    private String errorCode;
    private String errorMessage;
}
