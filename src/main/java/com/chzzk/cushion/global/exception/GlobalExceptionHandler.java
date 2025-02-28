package com.chzzk.cushion.global.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.util.Set;
import java.util.stream.Collectors;

import static com.chzzk.cushion.global.exception.ErrorCode.FILE_SIZE_EXCEEDED;
import static com.chzzk.cushion.global.exception.ErrorCode.NOT_UPLOAD_FILES;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CushionException.class)
    public ResponseEntity<ErrorResponse> handleBalanceTalkException(CushionException e) {
        ErrorResponse response = ErrorResponse.from(e.getErrorCode().getHttpStatus(), e.getMessage());
        log.error("exception message = {}", e.getMessage());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        log.error("exception message = {}", message);
        return ErrorResponse.from(BAD_REQUEST, message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));
        log.error("exception message = {}", message);
        ErrorResponse response = ErrorResponse.from(HttpStatus.BAD_REQUEST, message);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ErrorResponse handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("exception message = {}", e.getMessage());
        return ErrorResponse.from(FILE_SIZE_EXCEEDED.getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    public ErrorResponse handleMultipartException(MultipartException e) {
        log.error("exception message = {}", e.getMessage());
        return ErrorResponse.from(NOT_UPLOAD_FILES.getHttpStatus(), NOT_UPLOAD_FILES.getMessage());
    }
}
