package com.mentoring.assignment.membership.domain.memberpoint.web;

import jakarta.validation.constraints.NotNull;
import lombok.*;


// Wrapper Class vs primitive Type
// primitive Type: null 허용 X, false/true 만 가능, getter 사용시 is로 꺼낸다
// WrapperClass: null/false/true, getter 사용시 get 으로 꺼낸다
// Primitive Type 으로 선언할 시 @NotNull 동작X
@Getter
// [Error] Cannot construct instance of 'test.model.CreateUser' (no Creators, like default construct, exist) means you need no-args constructor.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointRequest {
    @NotNull
    private Long partnerId;
    @NotNull private String barcodeNumber;
    @NotNull private Integer amount;

    @Builder // 테스트할때 필요
    public PointRequest(Long partnerId, String barcodeNumber, Integer amount) {
        this.partnerId = partnerId;
        this.barcodeNumber = barcodeNumber;
        this.amount = amount;
    }
}
