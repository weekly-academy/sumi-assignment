package com.mentoring.assignment.membership.domain.memberpoint.mapper;

import com.mentoring.assignment.membership.domain.memberpoint.application.MemberPointService;
import com.mentoring.assignment.membership.domain.memberpoint.web.PointRequest;
import com.mentoring.assignment.membership.domain.memberpoint.web.PointResponse;
import org.mapstruct.Mapper;

// Mapper -> 구현 안되는 문제 serviceDTO에 setter나 builder가 없어서 셋팅해줄 수가 없었음
@Mapper(componentModel = "spring")
public interface PointMapper {
    MemberPointService.PointCommand toCommand (PointRequest pointRequest);

    PointResponse toResponse (MemberPointService.PointResult pointResult);
}
