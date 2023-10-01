package com.mentoring.assignment.membership.domain.pointhistory.application;

import com.mentoring.assignment.membership.domain.member.web.dto.PointResponse;
import com.mentoring.assignment.membership.global.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

//@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
// 클래스 레벨에는 공통적으로 적용되는 읽기전용 트랜잭션 어노테이션을 선언
// 추가나 삭제 또는 수정이 있는 작업에는 쓰기가 가능하도록 별도로 @Transacional 어노테이션을 메소드에 선언하는 것이 좋다
public class PointHistoryServiceImpl implements PointHistoryService {
    @Override
    public CommonResponse<List<PointResponse>> getPointHistoryList(LocalDateTime starTime, LocalDateTime endTime, String barcode) {
        return null;
    }
}
