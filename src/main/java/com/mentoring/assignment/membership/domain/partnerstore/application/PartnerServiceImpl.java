package com.mentoring.assignment.membership.domain.partnerstore.application;

import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerStoreCategoryRepository;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerStoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//@Slf4j
@Service
@RequiredArgsConstructor

public class PartnerServiceImpl implements PartnerService{

    private final PartnerStoreRepository partnerStoreRepository;
    private final PartnerStoreCategoryRepository partnerStoreCategoryRepository;

    // 가맹점 검증
    @Override
    public Boolean validatePartner(Long partnerId) {
        return null;
    }

    // 가맹점의 업종 ID 찾기
    private Long findCategoryId(Long partnerId) {
        return null;
    }
}
