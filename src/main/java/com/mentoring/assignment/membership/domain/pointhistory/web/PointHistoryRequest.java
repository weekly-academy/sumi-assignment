package com.mentoring.assignment.membership.domain.pointhistory.web;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Getter
@Setter // setter가 있어야 param으로 들어온 값 설정가능 -> null 값 에러 X
public class PointHistoryRequest {
    // LocalDateTime + @DateTimeFormat 사용하면 매치 에러 안뜸
    // -> OffsetDateTime은 다르게 설정해야함


    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    @NotNull
    private String barcodeNumber;

}
