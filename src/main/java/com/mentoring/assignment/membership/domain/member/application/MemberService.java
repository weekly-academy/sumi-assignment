package com.mentoring.assignment.membership.domain.member.application;

import com.mentoring.assignment.membership.domain.member.web.dto.PointRequest;
import com.mentoring.assignment.membership.global.dto.CommonResponse;
import com.mentoring.assignment.membership.domain.member.web.dto.PointResponse;

public interface MemberService {

    CommonResponse<String> issueBarcode(Long memberId) throws Exception;

    CommonResponse<PointResponse> savePoint(PointRequest pointRequest) throws Exception;

    CommonResponse<PointResponse> usePoint(PointRequest pointRequest) throws Exception;


}