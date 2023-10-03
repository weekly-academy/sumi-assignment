package com.mentoring.assignment.membershippoint.domain.member.web.dto;



import com.mentoring.assignment.membershippoint.domain.pointhistory.infrastructure.PointHistory;
import com.mentoring.assignment.membershippoint.domain.pointhistory.infrastructure.Type;
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


}
