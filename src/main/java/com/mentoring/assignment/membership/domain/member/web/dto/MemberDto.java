package com.mentoring.assignment.membership.domain.member.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@NoArgsConstructor // test에서 json parsing 할 때 생성자 필요
public class MemberDto {
    @NotNull
    private Long userId;


    @Builder
    public MemberDto(Long userId) {
        this.userId = userId;
    }

}
