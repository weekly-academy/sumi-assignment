package com.mentoring.assignment.membership.domain.member.web;

import com.mentoring.assignment.membership.domain.member.application.MemberService;
import com.mentoring.assignment.membership.domain.member.web.dto.PointRequest;
import com.mentoring.assignment.membership.domain.member.web.dto.PointResponse;
import com.mentoring.assignment.membership.global.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
// RequiredArgsConstructor: final이나 @Nonnull인 필드 값만 파라미터로 받는 생성자를 생성
// -> Service를 final로 불러오는 이유
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    // 멤버쉽 바코드 발급 API
    @PostMapping("/barcode")
    public ResponseEntity<CommonResponse<String>> createBarcode(@RequestBody Long userId) throws Exception {
        return new ResponseEntity<>(memberService.issueBarcode(userId), HttpStatus.OK);
    }

    // 포인트 적립 API
    // 왜 final을 쓰는 지 알아볼것
    @PostMapping("/point/saving")
    public ResponseEntity<CommonResponse<PointResponse>> savePoint(@RequestBody @Valid final PointRequest pointRequest) throws Exception {
        return new ResponseEntity<>(memberService.savePoint(pointRequest), HttpStatus.OK);
    }

    // 포인트 사용 API
    @PostMapping("/point/using")
    public ResponseEntity<CommonResponse<PointResponse>> usePoint(@RequestBody @Valid final PointRequest pointRequest) throws Exception {
        return new ResponseEntity<>(memberService.usePoint(pointRequest), HttpStatus.OK);
    }

}
