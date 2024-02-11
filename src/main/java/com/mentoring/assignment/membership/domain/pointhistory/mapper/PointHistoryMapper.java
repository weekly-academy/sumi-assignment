package com.mentoring.assignment.membership.domain.pointhistory.mapper;

import com.mentoring.assignment.membership.domain.pointhistory.application.PointHistoryService;
import com.mentoring.assignment.membership.domain.pointhistory.web.PointHistoryRequest;
import com.mentoring.assignment.membership.domain.pointhistory.web.PointHistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PointHistoryMapper {
    PointHistoryMapper INSTANCE = Mappers.getMapper(PointHistoryMapper.class);

    PointHistoryService.PointHistoryCommand toCommand (PointHistoryRequest pointHistoryRequest);

    List<PointHistoryResponse> toResponse (List<PointHistoryService.PointHistoryResult> pointHistoryResult);


}
