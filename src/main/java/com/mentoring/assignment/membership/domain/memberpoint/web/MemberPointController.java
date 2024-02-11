package com.mentoring.assignment.membership.domain.memberpoint.web;


import com.mentoring.assignment.membership.domain.memberpoint.application.MemberPointService;
import com.mentoring.assignment.membership.domain.memberpoint.mapper.PointMapper;
import com.mentoring.assignment.membership.global.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/point")
@Slf4j
public class MemberPointController {

    private final PointMapper mapper;

    private final MemberPointService memberPointService;

    @Operation(summary = "멤버쉽 포인트 적립 API", description = "가맹점의 업종별로 통합하여 포인트 적립")
    @PostMapping("/saving")
    public ResponseEntity<CommonResponse<PointResponse>> savePoint(@RequestBody @Valid PointRequest pointRequest) throws Exception {
        MemberPointService.PointCommand pointCommand = mapper.toCommand(pointRequest);
        log.info(pointCommand.toString());
        MemberPointService.PointResult pointResult = memberPointService.savePoint(pointCommand);
        log.info(pointResult.toString());
        CommonResponse<PointResponse> commonResponse = new CommonResponse<>(true, "포인트가 성공적으로 적립되었습니다.",mapper.toResponse(pointResult));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    // 포인트 사용 API
    @Operation(summary = "멤버쉽 포인트 사용 API", description = "요청한 가맹점의 업종별 통합 적립금에서 사용")
    @PostMapping("/using")
    public ResponseEntity<CommonResponse<PointResponse>> usePoint(@RequestBody @Valid PointRequest pointRequest) throws Exception {
        MemberPointService.PointCommand pointCommand = mapper.toCommand(pointRequest);
        MemberPointService.PointResult pointResult = memberPointService.usePoint(pointCommand);
        CommonResponse<PointResponse> commonResponse = new CommonResponse<>(true, "포인트가 성공적으로 사용되었습니다.",mapper.toResponse(pointResult));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
