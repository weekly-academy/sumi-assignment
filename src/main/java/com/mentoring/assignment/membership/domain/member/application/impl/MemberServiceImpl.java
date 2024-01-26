package com.mentoring.assignment.membership.domain.member.application.impl;



import com.mentoring.assignment.membership.domain.barcode.application.BarcodeService;
import com.mentoring.assignment.membership.domain.barcode.infrastructure.Barcode;
import com.mentoring.assignment.membership.domain.member.application.MemberService;
import com.mentoring.assignment.membership.domain.member.infrastructure.Member;
import com.mentoring.assignment.membership.domain.member.infrastructure.MemberPoint;
import com.mentoring.assignment.membership.domain.member.infrastructure.MemberPointRepository;
import com.mentoring.assignment.membership.domain.member.infrastructure.MemberRepository;
import com.mentoring.assignment.membership.domain.member.web.dto.PointRequest;
import com.mentoring.assignment.membership.domain.member.web.dto.PointResponse;
import com.mentoring.assignment.membership.domain.partnerstore.application.PartnerService;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerCategory;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerCategoryRepository;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerStore;
import com.mentoring.assignment.membership.domain.pointhistory.application.PointHistoryService;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.PointHistory;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.Type;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
// RequiredArgsConstructor: final이나 @Nonnull인 필드 값만 파라미터로 받는 생성자를 생성
// -> final로 불러오는 이유
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberPointRepository memberPointRepository;
    private final BarcodeService barcodeService;
    private final PartnerService partnerService;
    private final PointHistoryService pointHistoryService;

    private final PartnerCategoryRepository partnerCategoryRepository;


    @PostConstruct
    public void setup() {
        Member member1 = Member.builder()
                .build();

        Member member2 = Member.builder()
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);

    }


    // 멤버쉽 바코드 발급
    @Override
    public String[] issueBarcode(Long memberId) throws Exception {

        String[] arr = new String[2]; // 2개를 담을 수 있음

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new Exception("회원을 찾을 수 없습니다."));


        // 멤버가 바코드를 이미 가지고 있는지 검증
        if (member.getBarcode() != null) {

            arr[0] = "멤버가 바코드를 이미 가지고 있습니다.";
            arr[1] = member.getBarcode().getBarcodeNumber();
            return arr;
        } else {
            String newBarcodeNumber = barcodeService.createBarcode();
            Barcode barcode = Barcode.builder()
                    .barcodeNumber(newBarcodeNumber)
                    .member(member)
                    .build();

            barcodeService.save(barcode);
            member.setBarcode(barcode);
            memberRepository.save(member);

            MemberPoint memberPoint1 = MemberPoint
                    .builder()
                    .member(member)
                    .partnerCategory(partnerCategoryRepository.findById(1L).get())
                    .build();

            MemberPoint memberPoint2 = MemberPoint
                    .builder()
                    .member(member)
                    .partnerCategory(partnerCategoryRepository.findById(2L).get())
                    .build();

            MemberPoint memberPoint3 = MemberPoint
                    .builder()
                    .member(member)
                    .partnerCategory(partnerCategoryRepository.findById(3L).get())
                    .build();

            memberPointRepository.save(memberPoint1);
            memberPointRepository.save(memberPoint2);
            memberPointRepository.save(memberPoint3);

            arr[0] = "바코드를 생성했습니다.";
            arr[1] = newBarcodeNumber;


            return arr;


        }
    }

    // 멤버쉽 포인트 적립
    @Override
    public PointResponse savePoint(PointRequest pointRequest) throws Exception {

        // 바코드 검증
        Barcode barcode = barcodeService.validateBarcode(pointRequest.getBarcodeNumber());
        Member member = barcode.getMember();
        //가맹점 검증
        PartnerStore partnerstore = partnerService.validatePartner(pointRequest.getPartnerId());
        PartnerCategory partnerCategory = partnerstore.getPartnerCategory();

        // 업종과 멤버로 멤버포인트 찾기
        MemberPoint memberPoint = memberPointRepository.findByMemberAndPartnerCategory(member, partnerCategory)
                .orElseThrow(() -> new Exception("회원 포인트를 찾을 수 없습니다."));

        memberPoint.updatePoint(memberPoint.getCurrentPoint() + pointRequest.getAmount());

        // 멤버 포인트 저장
        memberPointRepository.save(memberPoint);

        // 포인트 히스토리에 저장
        LocalDateTime localDateTime = LocalDateTime.now();
        PointHistory pointHistory = pointHistoryService.savePointHistory(localDateTime, Type.EARN, memberPoint.getCurrentPoint(), partnerCategory, partnerstore, barcode);


        return new PointResponse(pointHistory);

    }

    // 멤버쉽 포인트 사용
    @Override
    public PointResponse usePoint(PointRequest pointRequest) throws Exception {
        // 바코드 검증
        Barcode barcode = barcodeService.validateBarcode(pointRequest.getBarcodeNumber());
        Member member = barcode.getMember();
        //가맹점 검증
        PartnerStore partnerstore = partnerService.validatePartner(pointRequest.getPartnerId());
        PartnerCategory partnerCategory = partnerstore.getPartnerCategory();

        // 업종과 멤버로 멤버포인트 찾기
        MemberPoint memberPoint = memberPointRepository.findByMemberAndPartnerCategory(member, partnerCategory)
                .orElseThrow(() -> new Exception("회원 포인트를 찾을 수 없습니다."));

        Integer currentPoint = memberPoint.getCurrentPoint();
        Integer amount = pointRequest.getAmount();

        // 현재 포인트에서 주어진 포인트를 뺏을 때 0보다 작으면 에러 발생
        if (Stream.of(currentPoint - amount)
                .anyMatch(result -> result < 0)) {
            throw new Exception("포인트가 부족합니다.");
        }

        memberPoint.updatePoint(memberPoint.getCurrentPoint() - pointRequest.getAmount());

        // 멤버 포인트 저장
        memberPointRepository.save(memberPoint);

        // 포인트 히스토리에 저장
        LocalDateTime localDateTime = LocalDateTime.now();
        PointHistory pointHistory = pointHistoryService.savePointHistory(localDateTime, Type.USE, amount, partnerCategory, partnerstore, barcode);


        return new PointResponse(pointHistory);

    }



}
