package com.mentoring.assignment.membership.domain.pointhistory.application;


import com.mentoring.assignment.membership.domain.barcode.application.BarcodeService;
import com.mentoring.assignment.membership.domain.barcode.infrastructure.Barcode;
import com.mentoring.assignment.membership.domain.member.web.dto.PointResponse;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerCategory;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerStore;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.PointHistory;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.PointHistoryRepository;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.Type;
import com.mentoring.assignment.membership.domain.pointhistory.web.PointHistoryRequest;

import com.mentoring.assignment.membership.global.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
// 클래스 레벨에는 공통적으로 적용되는 읽기전용 트랜잭션 어노테이션을 선언
// 추가나 삭제 또는 수정이 있는 작업에는 쓰기가 가능하도록 별도로 @Transacional 어노테이션을 메소드에 선언하는 것이 좋다
public class PointHistoryService{

    private final PointHistoryRepository pointHistoryRepository;

    private final BarcodeService barcodeService;


    public List<PointResponse> getPointHistory(PointHistoryRequest pointHistoryRequest) throws Exception {
        barcodeService.validateBarcode(pointHistoryRequest.getBarcodeNumber());
        List<PointHistory> pointHistoryList = pointHistoryRepository.findPointHistoryInTimeRange(pointHistoryRequest.getStartTime(), pointHistoryRequest.getEndTime());
//        log.info(pointHistoryList.toString());
        List<PointResponse> pointResponseList = new ArrayList<>();
        for (PointHistory pointHistory : pointHistoryList) {
            pointResponseList.add(PointResponse.builder().pointHistory(pointHistory).build());
        }

        return pointResponseList;
    }


    @Transactional
    public PointHistory savePointHistory(LocalDateTime approvedAt, Type type, Integer amount, PartnerCategory partnerCategory, PartnerStore partnerStore, Barcode barcode) {
        PointHistory pointHistory = PointHistory.builder()
                .approvedAt(approvedAt)
                .type(type)
                .amount(amount)
                .partnerCategory(partnerCategory)
                .partnerStore(partnerStore)
                .barcode(barcode)
                .build();

        pointHistoryRepository.save(pointHistory);
        return pointHistory;


    }

}
