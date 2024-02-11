package com.mentoring.assignment.membership.domain.partnerstore.application;


import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerStore;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartnerStoreReader {

    private final PartnerStoreRepository partnerStoreRepository;

    public PartnerStore validatePartner(Long partnerId) throws Exception {
        return partnerStoreRepository.findById(partnerId)
                .orElseThrow(() -> new Exception("등록되지 않은 가맹점입니다."));
    }

}
