package com.mentoring.assignment.membership.domain.member.mapper;

import com.mentoring.assignment.membership.domain.member.application.MemberService;
import com.mentoring.assignment.membership.domain.member.web.MemberResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberResponse toResponse (MemberService.IssuedBarcodeResult issuedBarcodeResult);
}
