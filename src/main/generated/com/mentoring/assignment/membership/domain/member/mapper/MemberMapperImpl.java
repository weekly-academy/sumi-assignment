package com.mentoring.assignment.membership.domain.member.mapper;

import com.mentoring.assignment.membership.domain.member.application.MemberService;
import com.mentoring.assignment.membership.domain.member.web.MemberResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-01T18:38:16+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberResponse toResponse(MemberService.IssuedBarcodeResult issuedBarcodeResult) {
        if ( issuedBarcodeResult == null ) {
            return null;
        }

        MemberResponse.MemberResponseBuilder memberResponse = MemberResponse.builder();

        memberResponse.barcodeNumber( issuedBarcodeResult.getBarcodeNumber() );

        return memberResponse.build();
    }
}
