package com.mentoring.assignment.membership.domain.member.application;

import com.mentoring.assignment.membership.domain.member.infrastructure.Member;
import com.mentoring.assignment.membership.domain.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReader {

    private final MemberRepository memberRepository;

    public Member findById(Long memberId) throws Exception {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new Exception("회원을 찾을 수 없습니다."));
    }
}
