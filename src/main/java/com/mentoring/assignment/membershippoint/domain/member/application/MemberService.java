package com.mentoring.assignment.membershippoint.domain.member.application;

import com.mentoring.assignment.membershippoint.domain.member.web.dto.PointRequest;
import com.mentoring.assignment.membershippoint.domain.member.web.dto.PointResponse;
import com.mentoring.assignment.membershippoint.global.dto.CommonResponse;

public interface MemberService {

    CommonResponse<String> issueBarcode(Long memberId) throws Exception;

    CommonResponse<PointResponse> savePoint(PointRequest pointRequest) throws Exception;

    CommonResponse<PointResponse> usePoint(PointRequest pointRequest) throws Exception;


}