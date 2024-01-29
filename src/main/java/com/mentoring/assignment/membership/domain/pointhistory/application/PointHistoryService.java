package com.mentoring.assignment.membership.domain.pointhistory.application;


import com.mentoring.assignment.membership.domain.barcode.application.BarcodeReader;
import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.Field;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.PointHistory;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.Type;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
// 클래스 레벨에는 공통적으로 적용되는 읽기전용 트랜잭션 어노테이션을 선언
// 추가나 삭제 또는 수정이 있는 작업에는 쓰기가 가능하도록 별도로 @Transacional 어노테이션을 메소드에 선언하는 것이 좋다
public class PointHistoryService{

    private final PointHistoryManager pointHistoryManager;

    private final BarcodeReader barcodeReader;



    public List<PointHistoryResult> getPointHistory(PointHistoryCommand pointHistoryCommand) throws Exception {
        // 바코드 검증
        barcodeReader.validateBarcode(pointHistoryCommand.getBarcodeNumber());

        List<PointHistory> pointHistoryList = pointHistoryManager.findPointHistoryInTimeRangeByBarcode(pointHistoryCommand.getStartTime(), pointHistoryCommand.getEndTime(), pointHistoryCommand.getBarcodeNumber());

        List<PointHistoryResult> pointHistoryResults = new ArrayList<>();
        for (PointHistory pointHistory : pointHistoryList) {
            pointHistoryResults.add(PointHistoryResult.builder().pointHistory(pointHistory).build());
        }

        return pointHistoryResults;
    }

    // serviceDto와 Controller에서 DTO가 같을때는 그냥 Request, Response를 application layer로 옮기는 게 낫나?

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PointHistoryCommand {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String barcodeNumber;

        @Builder
        public PointHistoryCommand(LocalDateTime startTime, LocalDateTime endTime, String barcodeNumber){
            this.startTime = startTime;
            this.endTime = endTime;
            this.barcodeNumber = barcodeNumber;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PointHistoryResult {
        private LocalDateTime approvedAt;
        private Type type;
        private Field field;
        private String partnerName;
        private Integer amount;

        @Builder
        public PointHistoryResult(PointHistory pointHistory) {
            this.approvedAt = pointHistory.getApprovedAt();
            this.type = pointHistory.getType();
            this.field = pointHistory.getPartnerCategory().getField();
            this.partnerName = pointHistory.getPartnerStore().getPartnerName();
            this.amount = pointHistory.getAmount();
        }


    }


}
