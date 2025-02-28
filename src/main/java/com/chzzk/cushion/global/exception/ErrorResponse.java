package com.chzzk.cushion.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private HttpStatus httpStatus;
    private String message;

    public static ErrorResponse from(HttpStatus httpStatus, String message) {
        return new ErrorResponse(httpStatus, message);
    }
}
