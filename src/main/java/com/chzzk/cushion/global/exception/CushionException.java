package com.chzzk.cushion.global.exception;

import lombok.Getter;

@Getter
public class CushionException extends RuntimeException {

    private final ErrorCode errorCode;

    public CushionException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
