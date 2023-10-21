package com.mentoring.assignment.membershippoint.domain.pointhistory.application;


import com.mentoring.assignment.membershippoint.domain.barcode.application.BarcodeService;
import com.mentoring.assignment.membershippoint.domain.barcode.infrastructure.Barcode;
import com.mentoring.assignment.membershippoint.domain.member.web.dto.PointResponse;
import com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure.PartnerCategory;
import com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure.PartnerStore;
import com.mentoring.assignment.membershippoint.domain.pointhistory.infrastructure.PointHistory;
import com.mentoring.assignment.membershippoint.domain.pointhistory.infrastructure.Type;
import com.mentoring.assignment.membershippoint.global.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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


    public CommonResponse<List<PointResponse>> getPointHistory(OffsetDateTime starTime, OffsetDateTime endTime, String barcodeNumber) throws Exception {
        barcodeService.validateBarcode(barcodeNumber);
        List<PointHistory> pointHistoryList = pointHistoryRepository.findPointHistoryInTimeRange(starTime, endTime);
        log.info(pointHistoryList.toString());
        List<PointResponse> pointResponseList = new ArrayList<>();
        for (PointHistory pointHistory : pointHistoryList) {
            pointResponseList.add(PointResponse.builder().pointHistory(pointHistory).build());
        }

        return new CommonResponse<>(true, "내역 목록이 조회되었습니다.", pointResponseList);
    }


    public PointHistory savePointHistory(OffsetDateTime approvedAt, Type type, Integer amount, PartnerCategory partnerCategory, PartnerStore partnerStore, Barcode barcode) {
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
