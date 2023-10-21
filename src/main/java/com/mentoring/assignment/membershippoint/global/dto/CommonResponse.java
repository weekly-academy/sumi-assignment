package com.mentoring.assignment.membershippoint.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommonResponse<T> {
    // null이 들어갈 일이 없다고 판단되지만,
    // isXXX를 사용하면 is가 자동으로 삭제되는 이슈로 Wrapper Class 사용

    private Boolean isSuccess;

    private String message;

    private T data;
}
