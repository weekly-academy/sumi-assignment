package com.mentoring.assignment.membershippoint.domain.member.web.dto;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure.Field;
import com.mentoring.assignment.membershippoint.domain.pointhistory.infrastructure.PointHistory;
import com.mentoring.assignment.membershippoint.domain.pointhistory.infrastructure.Type;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointResponse {
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime approvedAt;
    private Type type;
    private Field field;
    private String partnerName;
    private Integer amount;

    @Builder
    public PointResponse (PointHistory pointHistory){
        this.approvedAt = pointHistory.getApprovedAt();
        this.type = pointHistory.getType();
        this.field = pointHistory.getPartnerCategory().getField();
        this.partnerName = pointHistory.getPartnerStore().getPartnerName();
        this.amount = pointHistory.getAmount();
    }


}
