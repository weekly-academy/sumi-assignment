package com.mentoring.assignment.membershippoint.domain.pointhistory.web;

import com.mentoring.assignment.membershippoint.domain.member.web.dto.PointResponse;
import com.mentoring.assignment.membershippoint.domain.pointhistory.application.PointHistoryService;
import com.mentoring.assignment.membershippoint.global.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointHistoryController {

    private PointHistoryService pointHistoryService;

    @GetMapping("/history")
    public ResponseEntity<CommonResponse<List<PointResponse>>> getPointHistory(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") OffsetDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") OffsetDateTime endTime,
            @RequestParam(required = false) String barcodeNumber) throws Exception {

        return new ResponseEntity<>(pointHistoryService.getPointHistory(startTime, endTime, barcodeNumber), HttpStatus.OK);
    }



}

