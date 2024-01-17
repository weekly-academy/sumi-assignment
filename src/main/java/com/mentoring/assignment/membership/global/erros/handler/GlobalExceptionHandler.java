package com.mentoring.assignment.membership.global.erros.handler;

import com.mentoring.assignment.membership.global.erros.errorcode.CommonErrorCode;
import com.mentoring.assignment.membership.global.erros.errorcode.ErrorCode;
import com.mentoring.assignment.membership.global.erros.exception.RestApiException;
import com.mentoring.assignment.membership.global.erros.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleMemberShipException(final RestApiException e){
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(final IllegalArgumentException e) {
        log.warn("handleIllegalArgument", e);
        final ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, e.getMessage());
    }


    @Override
    // OVerride Error -> HttpStatus를 HttpStatusCode로 변경했더니 해결
    // @Valid에 의한 MethodArgumentNotValidException
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        log.warn("handleIllegalArgument", e);
        final ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(e, errorCode);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(final Exception ex) {
        log.warn("handleAllException", ex);
        final ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }



    private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(final ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }


    private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode, final String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(final ErrorCode errorCode, final String message) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(final BindException e, final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(e, errorCode));
    }

    private ErrorResponse makeErrorResponse(final BindException e, final ErrorCode errorCode) {
        // MethodArgumentNotValidException의 경우에는 에러 필드와 메세지를 추가해주어야함 -> getBindingResult
        final List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)// 각 필드 에러를 ValidationError 타입으로 변환
                .collect(Collectors.toList()); // 각 필드 에러에 대한 ValidationError 객체를 담고있는 list 얻기

        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .errors(validationErrorList)
                .build();
    }
}
