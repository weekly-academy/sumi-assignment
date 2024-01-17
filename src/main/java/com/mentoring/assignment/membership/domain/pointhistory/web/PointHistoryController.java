package com.mentoring.assignment.membership.domain.pointhistory.web;



import com.mentoring.assignment.membership.domain.member.web.dto.PointResponse;
import com.mentoring.assignment.membership.domain.pointhistory.application.PointHistoryService;

import com.mentoring.assignment.membership.global.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "포인트 내역 조회 API", description = "포인트 적립/사용 내역을 조회" )
    @GetMapping("/history")
    public ResponseEntity<CommonResponse<List<PointResponse>>> getPointHistory(PointHistoryRequest pointHistoryRequest) throws Exception {
//        log.info(pointHistoryRequest.getBarcodeNumber());
        return new ResponseEntity<>(pointHistoryService.getPointHistory(pointHistoryRequest), HttpStatus.OK);
    }



}

