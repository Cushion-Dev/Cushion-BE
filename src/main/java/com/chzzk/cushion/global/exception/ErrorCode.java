package com.chzzk.cushion.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400
    FILE_SIZE_EXCEEDED(BAD_REQUEST, "파일 크기가 초과되었습니다."),
    EMPTY_JWT_TOKEN(BAD_REQUEST, "토큰 값이 존재하지 않습니다."),
    INVALID_RELATIONSHIP(BAD_REQUEST, "잘못된 관계 입력입니다."),

    // 401
    MISMATCHED_EMAIL_OR_PASSWORD(UNAUTHORIZED, "이메일 또는 비밀번호가 잘못되었습니다."),
    AUTHENTICATION_ERROR(UNAUTHORIZED, "인증 오류가 발생했습니다."),
    BAD_CREDENTIAL_ERROR(UNAUTHORIZED, "로그인에 실패했습니다."),
    AUTHENTICATION_REQUIRED(UNAUTHORIZED, "인증이 필요합니다."),
    EXPIRED_JWT_TOKEN(UNAUTHORIZED, "만료된 토큰 입니다."),
    INVALID_JWT_TOKEN(UNAUTHORIZED, "유효하지 않은 토큰입니다"),
    UNAUTHORIZED_REISSUE_TOKEN(UNAUTHORIZED, "로그아웃 상태에서 토큰을 재발급 받을 수 없습니다."),
    NEED_LOGIN(UNAUTHORIZED, "로그인이 필요합니다."),

    // 403

    // 404
    NOT_FOUND_MEMBER(NOT_FOUND, "존재하지 않는 회원입니다."),
    NOT_FOUND_CHAT_ROOM(NOT_FOUND, "존재하지 않는 채팅방입니다."),
    NOT_FOUND_CHAT_ROOM_THAT_MEMBER(NOT_FOUND, "해당 회원에게 존재하지 않는 채팅방입니다."),


    // 409
    ALREADY_REGISTERED_EMAIL(CONFLICT, "이미 등록된 이메일입니다.");

    // 500


    private final HttpStatus httpStatus;
    private final String message;
}
