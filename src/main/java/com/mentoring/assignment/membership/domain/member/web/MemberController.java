package com.mentoring.assignment.membership.domain.member.web;

import com.mentoring.assignment.membership.domain.member.application.MemberService;

import com.mentoring.assignment.membership.domain.member.mapper.MemberMapper;
import com.mentoring.assignment.membership.global.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
// RequiredArgsConstructor: final이나 @Nonnull인 필드 값만 파라미터로 받는 생성자를 생성
// -> Service를 final로 불러오는 이유
@RequestMapping("/member")
@Slf4j
public class MemberController {


    private final MemberService memberService;

    private final MemberMapper mapper;

    // 멤버쉽 바코드 발급 API
    @Operation(summary = "멤버쉽 바코드 발급 API", description = "사용자 별로 하나의 멤버십 바코드를 발급 / 중복 불가능")
    @PostMapping("/barcode")
    public ResponseEntity<CommonResponse<MemberResponse>> createBarcode(@RequestBody @Valid MemberRequest memberRequest) throws Exception {
        MemberService.IssuedBarcodeResult result = memberService.issueBarcode(memberRequest.getUserId());
        log.info(result.toString());
        MemberResponse memberResponse = mapper.toResponse(result);
        CommonResponse<MemberResponse> commonResponse = new CommonResponse<>(true, "멤버쉽 바코드가 정상적으로 발급되었습니다.", memberResponse);
        return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);



    }



}
