package com.mentoring.assignment.membershippoint.domain.member.application;

import com.mentoring.assignment.membershippoint.domain.barcode.application.BarcodeService;
import com.mentoring.assignment.membershippoint.domain.barcode.infrastructure.Barcode;
import com.mentoring.assignment.membershippoint.domain.member.infrastructure.Member;
import com.mentoring.assignment.membershippoint.domain.member.infrastructure.MemberPoint;
import com.mentoring.assignment.membershippoint.domain.member.infrastructure.MemberPointRepository;
import com.mentoring.assignment.membershippoint.domain.member.infrastructure.MemberRepository;
import com.mentoring.assignment.membershippoint.domain.member.web.dto.PointRequest;
import com.mentoring.assignment.membershippoint.domain.member.web.dto.PointResponse;
import com.mentoring.assignment.membershippoint.domain.partnerstore.application.PartnerService;
import com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure.PartnerCategory;
import com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure.PartnerCategoryRepository;
import com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure.PartnerStore;
import com.mentoring.assignment.membershippoint.domain.pointhistory.application.PointHistoryService;
import com.mentoring.assignment.membershippoint.global.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
// RequiredArgsConstructor: final이나 @Nonnull인 필드 값만 파라미터로 받는 생성자를 생성
// -> final로 불러오는 이유
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MemberPointRepository memberPointRepository;
    private final BarcodeService barcodeService;
    private final PartnerService partnerService;

    private final PointHistoryService pointHistoryService;


    // 멤버쉽 바코드 발급
    @Override
    public CommonResponse<String> issueBarcode(Long memberId) throws Exception {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new Exception("회원을 찾을 수 없습니다."));


        // 멤버가 바코드를 이미 가지고 있는지 검증
        if (member.getBarcode() != null) {

            return new CommonResponse<>(true, "멤버가 바코드를 이미 가지고 있습니다.", member.getBarcode().getBarcodeNumber());
        } else {
            String newBarcodeNumber = barcodeService.createBarcode();
            log.info(newBarcodeNumber);
            member.getBarcode().updateBarcode(newBarcodeNumber);
            memberRepository.save(member);

            return new CommonResponse<>(true, "멤버가 새롭게 바코드를 발급받았습니다.", newBarcodeNumber);


        }
    }

    // 멤버쉽 포인트 적립
    // 1. 바코드 서비스: 바코드 검증 로직
    // 2. 가맹점 서비스: 가맹점 검증 로직
    // 3. 포인트 서비스: 업종별로 최종 포인트 저장 로직
    // 4. 포인트히스토리 서비스: 포인트 히스토리 저장 로직
    @Override
    public CommonResponse<PointResponse> savePoint(PointRequest pointRequest) throws Exception {

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

//        pointHistoryService.



        return null;
    }

    // 멤버쉽 포인트 사용
    // 1. 바코드 서비스: 바코드 검증 로직
    // 2. 가맹점 서비스: 가맹점 검증 로직
    // 3. 포인트 서비스: 업종별로 최종 포인트 사용 로직 -> 부족시 에러
    // 4. 포인트히스토리 서비스: 포인트 히스토리 저장 로직

    @Override
    public CommonResponse<PointResponse> usePoint(PointRequest pointRequest) throws Exception {
        return null;
    }









}
