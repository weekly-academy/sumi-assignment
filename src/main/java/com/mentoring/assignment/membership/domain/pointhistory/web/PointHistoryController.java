package com.mentoring.assignment.membership.domain.pointhistory.web;

import com.mentoring.assignment.membership.domain.member.web.dto.PointResponse;
import com.mentoring.assignment.membership.domain.pointhistory.application.PointHistoryService;
import com.mentoring.assignment.membership.global.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointHistoryController {

    private PointHistoryService pointHistoryService;

    // 멤버쉽 바코드 발급 API
    @GetMapping("/history")
    public ResponseEntity<CommonResponse<List<PointResponse>>> createBarcode(
            @RequestParam(value = "startTime", required = false) LocalDateTime startTime,
            @RequestParam(value = "endTime", required = false) LocalDateTime endTime,
            @RequestParam(value = "barcode", required = false) String barcode) throws Exception {
        return new ResponseEntity<>(pointHistoryService.getPointHistoryList(startTime, endTime, barcode), HttpStatus.OK);
    }
}
