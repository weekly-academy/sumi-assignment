package com.mentoring.assignment.membership.domain.member.application;

import com.mentoring.assignment.membership.domain.barcode.application.BarcodeService;
import com.mentoring.assignment.membership.domain.barcode.infrastructure.Barcode;
import com.mentoring.assignment.membership.domain.member.infrastructure.Member;
import com.mentoring.assignment.membership.domain.member.infrastructure.MemberPoint;
import com.mentoring.assignment.membership.domain.member.infrastructure.MemberPointRepository;
import com.mentoring.assignment.membership.domain.member.infrastructure.MemberRepository;
import com.mentoring.assignment.membership.domain.partnerstore.application.PartnerService;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.Field;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerCategory;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerCategoryRepository;
import com.mentoring.assignment.membership.domain.pointhistory.application.PointHistoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    MemberPointRepository memberPointRepository;

    @Mock
    BarcodeService barcodeService;

    @Mock
    PartnerCategoryRepository partnerCategoryRepository;

    @Mock
    PartnerService partnerService;

    @Mock
    PointHistoryService pointHistoryService;


    @DisplayName("성공_멤버가_처음_바코드를_발행했을때")
    @Test
    void issueBarcodeWithoutBarcode() throws Exception {
        // given: 테스트에 사용하는 변수, 입력값 정의
        Long memberId = 1L;
        Member member = Member.builder()
                .id(memberId)
                .build();



        // 어떠한 파라미터가 의존관계에 있는 객체의 메소드에 삽입되더라도 항상 반환값은 지정한대로 같은 값만 반환되어 나온다
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
        given(barcodeService.createBarcode()).willReturn("12345678"); // 바코드 서비스에서 이 문자열로 리턴

        Long partnerId1 = 1L;
        Long partnerId2 = 2L;
        Long partnerId3 = 3L;

        PartnerCategory partnerCategory1 = PartnerCategory.builder()
                        .field(Field.A)
                        .build();

        PartnerCategory partnerCategory2 = PartnerCategory.builder()
                .field(Field.B)
                .build();

        PartnerCategory partnerCategory3 = PartnerCategory.builder()
                .field(Field.C)
                .build();

        given(partnerCategoryRepository.findById(partnerId1)).willReturn(Optional.ofNullable(partnerCategory1));
        given(partnerCategoryRepository.findById(partnerId2)).willReturn(Optional.ofNullable(partnerCategory2));
        given(partnerCategoryRepository.findById(partnerId3)).willReturn(Optional.ofNullable(partnerCategory3));

        // when: 실제 액션을 하는 테스트 진행
        String[] result = memberService.issueBarcode(memberId);

        // then: 검증
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals("바코드를 생성했습니다.", result[0]);
        assertEquals("12345678", result[1]);

        // 메소드 호출 여부 검사
        // times: 호출 횟수 검사
        verify(memberRepository, times(1)).findById(memberId);
        verify(barcodeService, times(1)).createBarcode();
        // any: Barcode class의 어떠한 인스턴스도 허용
        verify(barcodeService, times(1)).save(any(Barcode.class));
    }

    @DisplayName("성공_멤버가_이미_바코드를_가지고있을때")
    @Test
    void issueBarcodeWithBarcode() throws Exception {
        // given: 테스트에 사용하는 변수, 입력값 정의
        Long memberId = 1L;

        Barcode barcode = Barcode.builder()
                .barcodeNumber("12345678")
                .build();

        Member member = Member.builder()
                .id(memberId)
                .barcode(barcode)
                .build();

        barcode.assignMember(member);

        // 어떠한 파라미터가 의존관계에 있는 객체의 메소드에 삽입되더라도 항상 반환값은 지정한대로 같은 값만 반환되어 나온다
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));


        // when: 실제 액션을 하는 테스트 진행
        String[] result = memberService.issueBarcode(memberId);

        // then: 검증
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals("멤버가 바코드를 이미 가지고 있습니다.", result[0]);
        assertEquals("12345678", result[1]);

        // 메소드 호출 여부 검사
        // times: 호출 횟수 검사
        verify(memberRepository, times(1)).findById(memberId);
        verify(barcodeService, never()).createBarcode();
        // any: Barcode class의 어떠한 인스턴스도 허용
        verify(barcodeService, never()).save(any(Barcode.class));
    }

    @Test
    void savePoint() {
    }

    @Test
    void usePoint() {
    }
}