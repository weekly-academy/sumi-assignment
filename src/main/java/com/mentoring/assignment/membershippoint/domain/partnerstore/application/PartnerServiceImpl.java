package com.mentoring.assignment.membershippoint.domain.partnerstore.application;


import com.mentoring.assignment.membershippoint.domain.barcode.infrastructure.Barcode;
import com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure.PartnerCategory;
import com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure.PartnerCategoryRepository;
import com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure.PartnerStore;
import com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure.PartnerStoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor

public class PartnerServiceImpl implements PartnerService{

    private final PartnerStoreRepository partnerStoreRepository;
    private final PartnerCategoryRepository partnerCategoryRepository;

    // 가맹점 검증
    @Override
    public PartnerStore validatePartner(Long partnerId) throws Exception {
        PartnerStore partnerStore = partnerStoreRepository.findById(partnerId)
                .orElseThrow(() -> new Exception("등록되지 않은 가맹점입니다."));

        return partnerStore;
    }


}
