package com.mentoring.assignment.membership.domain.pointhistory.mapper;

import com.mentoring.assignment.membership.domain.pointhistory.application.PointHistoryService;
import com.mentoring.assignment.membership.domain.pointhistory.web.PointHistoryRequest;
import com.mentoring.assignment.membership.domain.pointhistory.web.PointHistoryResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-29T23:26:37+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class PointHistoryMapperImpl implements PointHistoryMapper {

    @Override
    public PointHistoryService.PointHistoryCommand toCommand(PointHistoryRequest pointHistoryRequest) {
        if ( pointHistoryRequest == null ) {
            return null;
        }

        PointHistoryService.PointHistoryCommand.PointHistoryCommandBuilder pointHistoryCommand = PointHistoryService.PointHistoryCommand.builder();

        pointHistoryCommand.startTime( pointHistoryRequest.getStartTime() );
        pointHistoryCommand.endTime( pointHistoryRequest.getEndTime() );
        pointHistoryCommand.barcodeNumber( pointHistoryRequest.getBarcodeNumber() );

        return pointHistoryCommand.build();
    }

    @Override
    public List<PointHistoryResponse> toResponse(List<PointHistoryService.PointHistoryResult> pointHistoryResult) {
        if ( pointHistoryResult == null ) {
            return null;
        }

        List<PointHistoryResponse> list = new ArrayList<PointHistoryResponse>( pointHistoryResult.size() );
        for ( PointHistoryService.PointHistoryResult pointHistoryResult1 : pointHistoryResult ) {
            list.add( pointHistoryResultToPointHistoryResponse( pointHistoryResult1 ) );
        }

        return list;
    }

    protected PointHistoryResponse pointHistoryResultToPointHistoryResponse(PointHistoryService.PointHistoryResult pointHistoryResult) {
        if ( pointHistoryResult == null ) {
            return null;
        }

        PointHistoryResponse.PointHistoryResponseBuilder pointHistoryResponse = PointHistoryResponse.builder();

        pointHistoryResponse.approvedAt( pointHistoryResult.getApprovedAt() );
        pointHistoryResponse.type( pointHistoryResult.getType() );
        pointHistoryResponse.field( pointHistoryResult.getField() );
        pointHistoryResponse.partnerName( pointHistoryResult.getPartnerName() );
        pointHistoryResponse.amount( pointHistoryResult.getAmount() );

        return pointHistoryResponse.build();
    }
}
