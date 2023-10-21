package com.mentoring.assignment.membershippoint.domain.pointhistory.application;

import com.mentoring.assignment.membershippoint.domain.member.infrastructure.Member;
import com.mentoring.assignment.membershippoint.domain.pointhistory.infrastructure.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    // 포인트 히스토리 startTime과 endTime 사이의 컬럼들 조회
    // JPQL
    @Query(value = "select e from PointHistory e where e.approved_at between :startTime and :endTime", nativeQuery=true)
    List<PointHistory> findPointHistoryInTimeRange(
            @Param("startTime") OffsetDateTime startTime,
            @Param("endTime") OffsetDateTime endTime);
}
