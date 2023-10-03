package com.mentoring.assignment.membershippoint.domain.member.web.dto;


import com.sun.istack.NotNull;
import lombok.Getter;

// Wrapper Class vs primitive Type
// primitive Type: null 허용 X, false/true 만 가능, getter 사용시 is로 꺼낸다
// WrapperClass: null/false/true, getter 사용시 get 으로 꺼낸다
// Primitive Type 으로 선언할 시 @NotNull 동작X
@Getter
public class PointRequest {
    @NotNull
    private Long partnerId;
    @NotNull private String barcodeNumber;
    @NotNull private Integer amount;
}
