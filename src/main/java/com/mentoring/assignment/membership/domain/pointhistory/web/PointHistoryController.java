package com.mentoring.assignment.membership.domain.pointhistory.web;




import com.mentoring.assignment.membership.domain.pointhistory.application.PointHistoryService;

import com.mentoring.assignment.membership.domain.pointhistory.mapper.PointHistoryMapper;
import com.mentoring.assignment.membership.global.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
@Slf4j
public class PointHistoryController {

    private final PointHistoryService pointHistoryService;
    private final PointHistoryMapper mapper;

    @Operation(summary = "포인트 내역 조회 API", description = "포인트 적립/사용 내역을 조회" )
    @GetMapping("/history")
    public ResponseEntity<CommonResponse<List<PointHistoryResponse>>> getPointHistory(@Valid PointHistoryRequest pointHistoryRequest) throws Exception {
        PointHistoryService.PointHistoryCommand pointHistoryCommand = mapper.toCommand(pointHistoryRequest);
        List<PointHistoryService.PointHistoryResult> results = pointHistoryService.getPointHistory(pointHistoryCommand);
        CommonResponse<List<PointHistoryResponse>> commonResponses = new CommonResponse<>(true, "내역 목록이 조회되었습니다.", mapper.toResponse(results));
        return new ResponseEntity<>(commonResponses, HttpStatus.OK);
    }



}

