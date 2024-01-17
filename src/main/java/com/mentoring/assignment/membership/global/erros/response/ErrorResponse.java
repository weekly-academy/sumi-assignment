package com.mentoring.assignment.membership.global.erros.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;


@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {
    private final String code;
    private final String message;

    // 오류가 없는 경우에는 errors 필드가 비어있음
    // -> Json 직렬화시에 해당 필드 생략 (응답에 불필요하게 나타나지 않도록)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> errors;

    // @Valid를 사용했을 때 에러가 발생한 경우 어느 필드에서 에러가 발생했는지 응답을 위한 ValidationError 내부 정적 클래스로 추가
    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {
        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }

}
