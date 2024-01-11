package com.mentoring.assignment.membership.domain.member.web;

import com.mentoring.assignment.membership.domain.member.application.MemberService;
import com.mentoring.assignment.membership.domain.member.web.dto.MemberDto;
import com.mentoring.assignment.membership.domain.member.web.dto.PointRequest;
import com.mentoring.assignment.membership.domain.member.web.dto.PointResponse;

import com.mentoring.assignment.membership.global.dto.CommonResponse;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
// RequiredArgsConstructor: final이나 @Nonnull인 필드 값만 파라미터로 받는 생성자를 생성
// -> Service를 final로 불러오는 이유
@RequestMapping("/member")
public class MemberController {


    private final MemberService memberService;

    // 멤버쉽 바코드 발급 API
    @ApiOperation(value = "멤버쉽 바코드 발급 API", notes = "사용자 별로 하나의 멤버십 바코드를 발급 / 중복 불가능")
    @PostMapping("/barcode")
    public ResponseEntity<CommonResponse<String>> createBarcode(@RequestBody MemberDto memberDto) throws Exception {
        String[] arr = memberService.issueBarcode(memberDto.getUserId());
        CommonResponse<String> commonResponse = new CommonResponse<>(true, arr[0], arr[1]);
        return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
    }

    // 포인트 적립 API
    @ApiOperation(value = "멤버쉽 포인트 적립 API", notes = "가맹점의 업종별로 통합하여 포인트 적립")
    @PostMapping("/point/saving")
    public ResponseEntity<CommonResponse<PointResponse>> savePoint(@RequestBody @Valid PointRequest pointRequest) throws Exception {
        CommonResponse<PointResponse> commonResponse = new CommonResponse<>(true, "포인트가 성공적으로 적립되었습니다.",memberService.savePoint(pointRequest));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    // 포인트 사용 API
    @ApiOperation(value = "멤버쉽 포인트 사용 API", notes = "요청한 가맹점의 업종별 통합 적립금에서 사용")
    @PostMapping("/point/using")
    public ResponseEntity<CommonResponse<PointResponse>> usePoint(@RequestBody @Valid PointRequest pointRequest) throws Exception {
        CommonResponse<PointResponse> commonResponse = new CommonResponse<>(true, "포인트가 성공적으로 사용되었습니다.",memberService.usePoint(pointRequest));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
