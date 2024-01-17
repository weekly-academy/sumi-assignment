package com.mentoring.assignment.membership.global.erros.exception;


import com.mentoring.assignment.membership.global.erros.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException{
    private final ErrorCode errorCode;
}
