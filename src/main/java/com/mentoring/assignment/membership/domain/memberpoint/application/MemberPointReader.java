package com.mentoring.assignment.membership.domain.memberpoint.application;

import com.mentoring.assignment.membership.domain.member.infrastructure.Member;
import com.mentoring.assignment.membership.domain.memberpoint.infrastructure.MemberPoint;
import com.mentoring.assignment.membership.domain.memberpoint.infrastructure.MemberPointRepository;
import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.PartnerCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberPointReader {

    private final MemberPointRepository memberPointRepository;

    public MemberPoint findByMemberAndPartnerCategory(Member member, PartnerCategory partnerCategory) throws Exception {
        return memberPointRepository.findByMemberAndPartnerCategory(member, partnerCategory)
                .orElseThrow(() -> new Exception("회원 포인트를 찾을 수 없습니다."));
    }

}
