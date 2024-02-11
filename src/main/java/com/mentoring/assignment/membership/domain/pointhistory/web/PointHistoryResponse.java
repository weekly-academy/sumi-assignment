package com.mentoring.assignment.membership.domain.pointhistory.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.Field;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.PointHistory;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.Type;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistoryResponse {
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvedAt;
    private Type type;
    private Field field;
    private String partnerName;
    private Integer amount;

    @Builder
    public PointHistoryResponse(LocalDateTime approvedAt, Type type, Field field, String partnerName, Integer amount){
        this.approvedAt = approvedAt;
        this.type = type;
        this.field = field;
        this.partnerName = partnerName;
        this.amount = amount;
    }

}