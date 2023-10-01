package com.mentoring.assignment.membership.domain.member.web.dto;

import javax.validation.constraints.NotNull;

public class PointRequest {
    @NotNull private Long partnerId;
    @NotNull private Long barcodeId;
    @NotNull private int amount;
}
