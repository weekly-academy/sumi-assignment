package com.mentoring.assignment.membership.domain.barcode.application;

import com.mentoring.assignment.membership.domain.barcode.infrastructure.Barcode;
import com.mentoring.assignment.membership.domain.barcode.infrastructure.BarcodeRepository;
import com.mentoring.assignment.membership.domain.member.infrastructure.Member;
import com.mentoring.assignment.membership.domain.member.infrastructure.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BarcodeServiceTest {

    @InjectMocks
    private BarcodeService barcodeService;

    @Mock
    private BarcodeRepository barcodeRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("성공_바코드를_검증했을때")
    void validateBarcode() throws Exception {
        // given
        Barcode barcode = Barcode.builder()
                .barcodeNumber("123456789")
                .build();


        Member member = Member.builder()
                .barcode(barcode)
                .build();

        barcode.assignMember(member);

        // 불필요한 스텁 설정 에러
//        given(memberRepository.save(member)).willReturn(member);
//        given(barcodeRepository.save(barcode)).willReturn(barcode);


        given(barcodeRepository.findByBarcodeNumber(barcode.getBarcodeNumber())).willReturn(Optional.of(barcode));

        // when
        Barcode findBarcode = barcodeService.validateBarcode("123456789");

        // then
        Assertions.assertSame(barcode, findBarcode);
        Assertions.assertEquals(barcode.getMember().getId(), findBarcode.getMember().getId());
    }


}