package com.mentoring.assignment.membership.domain.memberpoint.application;

import com.mentoring.assignment.membership.domain.member.infrastructure.Member;
import com.mentoring.assignment.membership.domain.memberpoint.infrastructure.MemberPoint;
import com.mentoring.assignment.membership.domain.memberpoint.infrastructure.MemberPointRepository;
import com.mentoring.assignment.membership.domain.partnercategory.application.PartnerCategoryReader;
import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.Field;
import com.mentoring.assignment.membership.domain.pointhistory.infrastructure.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberPointManager {

    private final MemberPointRepository memberPointRepository;



    // 멤버 포인트 업데이트, 저장
    @Transactional
    public MemberPoint updateMemberPoint(MemberPoint memberPoint, Integer amount, Type type) throws Exception {

        if (type == Type.EARN) {
            memberPoint.updatePoint(memberPoint.getCurrentPoint() + amount);
        }

        else {

            Integer currentPoint = memberPoint.getCurrentPoint();

            // 현재 포인트에서 주어진 포인트를 뺏을 때 0보다 작으면 에러 발생
            if (Stream.of(currentPoint - amount)
                    .anyMatch(result -> result < 0)) {
                throw new Exception("포인트가 부족합니다.");
            }

            memberPoint.updatePoint(memberPoint.getCurrentPoint() - amount);
        }

    // 멤버 포인트 저장
        return memberPointRepository.save(memberPoint);
    }

    @Transactional
    public void initMemberPoint(Member member, PartnerCategoryReader partnerCategoryReader) throws Exception {
        //        Memberpoint 데이터베이스 ID가 1번이 아니면 어떡해? →  field를 이용하자 findByField 이용

        MemberPoint memberPoint1 = MemberPoint
                .builder()
                .member(member)
                .partnerCategory(partnerCategoryReader.findByField(Field.A))
                .build();

        MemberPoint memberPoint2 = MemberPoint
                .builder()
                .member(member)
                .partnerCategory(partnerCategoryReader.findByField(Field.B))
                .build();

        MemberPoint memberPoint3 = MemberPoint
                .builder()
                .member(member)
                .partnerCategory(partnerCategoryReader.findByField(Field.C))
                .build();

        memberPointRepository.save(memberPoint1);
        memberPointRepository.save(memberPoint2);
        memberPointRepository.save(memberPoint3);
    }




}
