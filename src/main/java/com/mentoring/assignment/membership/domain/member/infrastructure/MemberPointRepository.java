package com.mentoring.assignment.membership.domain.member.infrastructure;

import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberPointRepository extends JpaRepository<MemberPoint, Long> {

    Optional<MemberPoint> findByMemberAndPartnerCategory(Member member, PartnerCategory partnerCategory);

}
