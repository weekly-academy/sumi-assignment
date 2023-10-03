package com.mentoring.assignment.membershippoint.domain.pointhistory.application;


import com.mentoring.assignment.membershippoint.domain.member.web.dto.PointResponse;
import com.mentoring.assignment.membershippoint.global.dto.CommonResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface PointHistoryService {
    // 포인트 히스토리 내역 조회
    CommonResponse<List<PointResponse>> getPointHistory(LocalDateTime starTime, LocalDateTime endTime, String barcode);


}
