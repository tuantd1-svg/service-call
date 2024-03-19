package com.example.muadeeservice.enumable;

import lombok.Getter;

public enum ErrorCodeCore {
    SUCCESS("00", "Success"),
    SYSTEM_ERROR("99","System error"),

    UNKNOWN_ERROR("9999","Unknown error");
    @Getter
    private String code;
    @Getter
    private String message;

    ErrorCodeCore(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public static ErrorCodeCore map(String errorCode)
    {
        for(ErrorCodeCore errorCodeCore: ErrorCodeCore.values())
        {
            if(errorCodeCore.getCode().equals(errorCode))
                return errorCodeCore;
        }
        return UNKNOWN_ERROR;
    }
}
