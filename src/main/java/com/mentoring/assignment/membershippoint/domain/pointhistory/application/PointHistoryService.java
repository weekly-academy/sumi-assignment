package com.mentoring.assignment.membershippoint.domain.pointhistory.application;


import com.mentoring.assignment.membershippoint.domain.barcode.infrastructure.Barcode;
import com.mentoring.assignment.membershippoint.domain.member.web.dto.PointResponse;
import com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure.PartnerCategory;
import com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure.PartnerStore;
import com.mentoring.assignment.membershippoint.domain.pointhistory.infrastructure.PointHistory;
import com.mentoring.assignment.membershippoint.domain.pointhistory.infrastructure.Type;
import com.mentoring.assignment.membershippoint.global.dto.CommonResponse;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public interface PointHistoryService {
    // 포인트 히스토리 내역 조회
    CommonResponse<List<PointResponse>> getPointHistory(OffsetDateTime starTime, OffsetDateTime endTime, String barcodeNumber) throws Exception;

    PointHistory savePointHistory(OffsetDateTime approvedAt, Type type, Integer amount, PartnerCategory partnerCategory, PartnerStore partnerStore, Barcode barcode);

}
