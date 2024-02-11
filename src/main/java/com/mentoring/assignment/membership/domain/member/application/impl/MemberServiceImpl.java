package com.mentoring.assignment.membership.domain.member.application.impl;


import com.mentoring.assignment.membership.domain.barcode.infrastructure.Barcode;
import com.mentoring.assignment.membership.domain.member.application.MemberReader;
import com.mentoring.assignment.membership.domain.member.application.MemberService;
import com.mentoring.assignment.membership.domain.member.infrastructure.Member;
import com.mentoring.assignment.membership.domain.memberpoint.application.MemberPointManager;
import com.mentoring.assignment.membership.domain.partnercategory.application.PartnerCategoryReader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
// RequiredArgsConstructor: final이나 @Nonnull인 필드 값만 파라미터로 받는 생성자를 생성
// -> final로 불러오는 이유
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceImpl implements MemberService {


    private final MemberReader memberReader;
    private final PartnerCategoryReader partnerCategoryReader;
    private final MemberPointManager memberPointManager;

    // 멤버쉽 바코드 발급
    @Override
    @Transactional
    public IssuedBarcodeResult issueBarcode(Long memberId) throws Exception {

        // Member Reader를 이용하여 회원 찾기
        Member member = memberReader.findById(memberId);
        log.info(member.getId().toString());

        // 멤버가 바코드를 이미 가지고 있는지 검증
        if (member.getBarcode() != null) {
            throw new IllegalArgumentException("이미 바코드를 가지고 있습니다.");
        }

        Barcode barcode = Barcode.builder()
                .member(member)
                .build();

        barcode.create();
//        barcodeReader.save(barcode);
        log.info(barcode.getBarcodeNumber());
        // @transacational 있으니까 barcode save 안해도 되지 않나? -> transient 에러뜸
      // -> cascade 설정해줌 -> 안해도됨

        member.assignBarcode(barcode);
//        memberRepository.save(member); -> @transactional 있으니까 안해도 되지않나? -> 안해도됨

        log.info(member.getBarcode().getBarcodeNumber());
        // 멤버쉽 발급 후 분야별로 멤버포인트 초기화
        memberPointManager.initMemberPoint(member, partnerCategoryReader);

        return new IssuedBarcodeResult(barcode.getBarcodeNumber());


    }






}
