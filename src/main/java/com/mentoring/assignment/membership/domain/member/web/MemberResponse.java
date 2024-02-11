package com.mentoring.assignment.membership.domain.member.web;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {
    private String barcodeNumber;

    @Builder
    public MemberResponse(String barcodeNumber){
        this.barcodeNumber = barcodeNumber;
    }
}
