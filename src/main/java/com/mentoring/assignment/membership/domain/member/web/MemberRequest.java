package com.mentoring.assignment.membership.domain.member.web;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@NoArgsConstructor // test에서 json parsing 할 때 생성자 필요
public class MemberRequest {
    @NotNull
    private Long userId;


    @Builder
    public MemberRequest(Long userId) {
        this.userId = userId;
    }


}
