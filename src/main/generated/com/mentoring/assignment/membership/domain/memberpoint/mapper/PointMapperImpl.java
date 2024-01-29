package com.mentoring.assignment.membership.domain.memberpoint.mapper;

import com.mentoring.assignment.membership.domain.memberpoint.application.MemberPointService;
import com.mentoring.assignment.membership.domain.memberpoint.web.PointRequest;
import com.mentoring.assignment.membership.domain.memberpoint.web.PointResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-29T23:26:37+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class PointMapperImpl implements PointMapper {

    @Override
    public MemberPointService.PointCommand toCommand(PointRequest pointRequest) {
        if ( pointRequest == null ) {
            return null;
        }

        MemberPointService.PointCommand.PointCommandBuilder pointCommand = MemberPointService.PointCommand.builder();

        pointCommand.partnerId( pointRequest.getPartnerId() );
        pointCommand.barcodeNumber( pointRequest.getBarcodeNumber() );
        pointCommand.amount( pointRequest.getAmount() );

        return pointCommand.build();
    }

    @Override
    public PointResponse toResponse(MemberPointService.PointResult pointResult) {
        if ( pointResult == null ) {
            return null;
        }

        PointResponse.PointResponseBuilder pointResponse = PointResponse.builder();

        pointResponse.approvedAt( pointResult.getApprovedAt() );
        pointResponse.type( pointResult.getType() );
        pointResponse.field( pointResult.getField() );
        pointResponse.partnerName( pointResult.getPartnerName() );
        pointResponse.amount( pointResult.getAmount() );

        return pointResponse.build();
    }
}
