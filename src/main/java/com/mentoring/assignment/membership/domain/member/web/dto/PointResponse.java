package com.mentoring.assignment.membership.domain.member.web.dto;

import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.PointHistory;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.Type;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointResponse {
    private LocalDateTime approvedAt;
    private Type type;
    private char category;
    private String partnerName;
    private int amount;

    @Builder
    public PointResponse(final PointHistory pointHistory){
        this.approvedAt = pointHistory.getApprovedAt();
        this.type = pointHistory.getType();
        this.category = pointHistory.getPartnerStoreCategory().getField();
        this.partnerName = pointHistory.getPartnerStore().getPartnerName();
        this.amount = pointHistory.getAmount();

    }
}
