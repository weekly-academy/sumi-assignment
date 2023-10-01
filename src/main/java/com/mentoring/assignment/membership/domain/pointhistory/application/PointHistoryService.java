package com.mentoring.assignment.membership.domain.pointhistory.application;

import com.mentoring.assignment.membership.domain.member.web.dto.PointResponse;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.PointHistory;
import com.mentoring.assignment.membership.global.dto.CommonResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface PointHistoryService {
    // 포인트 히스토리 내역 조회
    CommonResponse<List<PointResponse>> getPointHistoryList(LocalDateTime starTime, LocalDateTime endTime, String barcode);


}
