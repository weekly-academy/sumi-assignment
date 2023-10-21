package com.mentoring.assignment.membership.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public class CommonResponse<T> {

    private Boolean isSuccess;

    private String message;

    private T data;



}
