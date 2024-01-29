package com.mentoring.assignment.membership.domain.memberpoint.application;

import com.mentoring.assignment.membership.domain.barcode.infrastructure.Barcode;
import com.mentoring.assignment.membership.domain.member.infrastructure.Member;
import com.mentoring.assignment.membership.domain.memberpoint.infrastructure.MemberPoint;
import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.Field;
import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.PartnerCategory;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerStore;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.PointHistory;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.Type;

import com.mentoring.assignment.membership.domain.pointhistory.application.PointHistoryManager;
import com.mentoring.assignment.membership.domain.barcode.application.BarcodeReader;
import com.mentoring.assignment.membership.domain.partnerstore.application.PartnerStoreReader;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberPointService {

    private final MemberPointReader memberPointReader;
    private final MemberPointManager memberPointManager;
    private final BarcodeReader barcodeReader;
    private final PartnerStoreReader partnerStoreReader;
    private final PointHistoryManager pointHistoryManager;

    // 포인트 적립
    @Transactional
    public PointResult savePoint(PointCommand pointCommand) throws Exception {
        // 바코드 검증
        Barcode barcode = barcodeReader.validateBarcode(pointCommand.barcodeNumber);
        Member member = barcode.getMember();
        // 가맹점 검증
        PartnerStore partnerStore = partnerStoreReader.validatePartner(pointCommand.partnerId);
        PartnerCategory partnerCategory = partnerStore.getPartnerCategory();

        // MemberPointReader를 이용해서 멤버포인트 찾기
        MemberPoint memberPoint = memberPointReader.findByMemberAndPartnerCategory(member, partnerCategory);
        memberPoint = memberPointManager.updateMemberPoint(memberPoint, pointCommand.amount, Type.EARN);
        log.info(memberPoint.toString());

        // pointHistoryManager를 이용해서 히스토리에 저장
        PointHistory pointHistory = pointHistoryManager.savePointHistory(Type.EARN, memberPoint.getCurrentPoint(), partnerCategory, partnerStore, barcode);
        log.info(pointHistory.toString());
        return PointResult.builder().pointHistory(pointHistory).build();
    }


    // 포인트 사용
    @Transactional
    public PointResult usePoint(PointCommand pointCommand) throws Exception {
        // 바코드 검증
        Barcode barcode = barcodeReader.validateBarcode(pointCommand.barcodeNumber);
        Member member = barcode.getMember();
        // 가맹점 검증
        PartnerStore partnerStore = partnerStoreReader.validatePartner(pointCommand.partnerId);
        PartnerCategory partnerCategory = partnerStore.getPartnerCategory();

        // MemberPointReader를 이용해서 멤버포인트 찾기
        MemberPoint memberPoint = memberPointReader.findByMemberAndPartnerCategory(member, partnerCategory);
        memberPointManager.updateMemberPoint(memberPoint, pointCommand.amount, Type.USE);

        // pointHistoryManager를 이용해서 히스토리에 저장
        PointHistory pointHistory = pointHistoryManager.savePointHistory(Type.USE, memberPoint.getCurrentPoint(), partnerCategory, partnerStore, barcode);
        return PointResult.builder().pointHistory(pointHistory).build();
    }

    // Service DTO

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class PointCommand {
        private Long partnerId;
        private String barcodeNumber;
        private Integer amount;

        @Builder
        public PointCommand(Long partnerId, String barcodeNumber, Integer amount) {
            this.partnerId = partnerId;
            this.barcodeNumber = barcodeNumber;
            this.amount = amount;
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class PointResult {
        private LocalDateTime approvedAt;
        private Type type;
        private Field field;
        private String partnerName;
        private Integer amount;

        @Builder
        public PointResult (PointHistory pointHistory){
            this.approvedAt = pointHistory.getApprovedAt();
            this.type = pointHistory.getType();
            this.field = pointHistory.getPartnerCategory().getField();
            this.partnerName = pointHistory.getPartnerStore().getPartnerName();
            this.amount = pointHistory.getAmount();
        }
    }



}
