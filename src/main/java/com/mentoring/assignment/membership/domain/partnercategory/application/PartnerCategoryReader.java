package com.mentoring.assignment.membership.domain.partnercategory.application;

import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.Field;
import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.PartnerCategory;
import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.PartnerCategoryRepository;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartnerCategoryReader {

    private final PartnerStoreRepository partnerStoreRepository;


    private final PartnerCategoryRepository partnerCategoryRepository;

    public PartnerCategory findByField(Field field) throws Exception {
        return partnerCategoryRepository.findByField(field)
                .orElseThrow(() -> new Exception("해당하는 업종이 존재하지않습니다."));
    }

    // 가맹점 검증
    public PartnerStore validatePartner(Long partnerId) throws Exception {
       return partnerStoreRepository.findById(partnerId)
                .orElseThrow(() -> new Exception("등록되지 않은 가맹점입니다."));

    }

}
