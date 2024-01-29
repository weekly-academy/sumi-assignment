package com.mentoring.assignment.membership.domain.pointhistory.application;


import com.mentoring.assignment.membership.domain.barcode.infrastructure.Barcode;
import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.PartnerCategory;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerStore;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.PointHistory;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.PointHistoryRepository;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointHistoryManager {

    private final PointHistoryRepository pointHistoryRepository;


    public List<PointHistory> findPointHistoryInTimeRangeByBarcode(LocalDateTime startTime, LocalDateTime endTime, String barcodeNumber) {
        return pointHistoryRepository.findPointHistoryInTimeRangeByBarcode(startTime, endTime, barcodeNumber);

    }

    @Transactional
    public PointHistory savePointHistory(Type type, int currentPoint, PartnerCategory partnerCategory, PartnerStore partnerstore, Barcode barcode) {
        LocalDateTime approvedAt = LocalDateTime.now();

        PointHistory pointHistory = PointHistory.builder()
                .approvedAt(approvedAt)
                .type(type)
                .amount(currentPoint)
                .partnerCategory(partnerCategory)
                .partnerStore(partnerstore)
                .barcode(barcode)
                .build();

        return pointHistoryRepository.save(pointHistory);
    }
}
