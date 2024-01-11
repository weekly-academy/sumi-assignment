package com.mentoring.assignment.membership.domain.member.application;


import com.mentoring.assignment.membership.domain.member.web.dto.PointRequest;
import com.mentoring.assignment.membership.domain.member.web.dto.PointResponse;

public interface MemberService {

    String[] issueBarcode(Long memberId) throws Exception;

    PointResponse savePoint(PointRequest pointRequest) throws Exception;

    PointResponse usePoint(PointRequest pointRequest) throws Exception;


}