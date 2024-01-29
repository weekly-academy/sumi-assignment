package com.mentoring.assignment.membership.domain.member.application;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public interface MemberService {


    IssuedBarcodeResult issueBarcode(Long memberId) throws Exception;


    @ToString
    @Getter
    public static class IssuedBarcodeResult {

        private final String barcodeNumber;


        public IssuedBarcodeResult(String barcodeNumber) {
            validate(barcodeNumber);
            this.barcodeNumber = barcodeNumber;
        }

        private void validate(String barcodeNumber) {
            if (barcodeNumber == null) {
                throw new IllegalArgumentException("barcodeNumber must not be null");
            }
        }

    }

}
