package com.mentoring.assignment.membership.domain.pointhistory.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    // 포인트 히스토리 startTime과 endTime 사이의 컬럼들 조회
    // JPQL -> PointHistory의 field 대소문자 구분 잘하기!
    @Query(value = "select p from PointHistory p where p.approvedAt between :startTime and :endTime", nativeQuery=false)
    List<PointHistory> findPointHistoryInTimeRange(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}
