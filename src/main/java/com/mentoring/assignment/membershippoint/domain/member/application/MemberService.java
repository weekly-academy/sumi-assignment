package com.mentoring.assignment.membershippoint.domain.member.application;

import com.mentoring.assignment.membershippoint.domain.member.web.dto.PointRequest;
import com.mentoring.assignment.membershippoint.domain.member.web.dto.PointResponse;

import java.util.Map;

public interface MemberService {

    String[] issueBarcode(Long memberId) throws Exception;

    PointResponse savePoint(PointRequest pointRequest) throws Exception;

    PointResponse usePoint(PointRequest pointRequest) throws Exception;


}