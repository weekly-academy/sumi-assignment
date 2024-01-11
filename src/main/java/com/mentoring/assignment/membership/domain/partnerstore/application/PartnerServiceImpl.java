package com.mentoring.assignment.membership.domain.partnerstore.application;


import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.*;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService{

    private final PartnerCategoryRepository partnerCategoryRepository;
    private final PartnerStoreRepository partnerStoreRepository;


    @PostConstruct
    public void setup() {

        PartnerCategory partnerCategory1 = PartnerCategory
                .builder()
                .field(Field.A)
                .build();

        PartnerCategory partnerCategory2 = PartnerCategory
                .builder()
                .field(Field.B)
                .build();

        PartnerCategory partnerCategory3 = PartnerCategory
                .builder()
                .field(Field.C)
                .build();


        PartnerStore partnerStore1 = PartnerStore
                .builder()
                .partnerName("A마트")
                .partnerCategory(partnerCategory1)
                .build();


        PartnerStore partnerStore2 = PartnerStore
                .builder()
                .partnerName("B마트")
                .partnerCategory(partnerCategory1)
                .build();

        // 의문점 ? 꼭 이렇게 연결된 것 save 한 뒤에 그 다음 것을 save 해야하는지 ? -> 이때 Cascade를 쓰는 것?

        partnerCategoryRepository.save(partnerCategory1);
        partnerCategoryRepository.save(partnerCategory2);
        partnerCategoryRepository.save(partnerCategory3);
        partnerStoreRepository.save(partnerStore1);
        partnerStoreRepository.save(partnerStore2);


    }

    // 가맹점 검증
    @Override
    public PartnerStore validatePartner(Long partnerId) throws Exception {
        PartnerStore partnerStore = partnerStoreRepository.findById(partnerId)
                .orElseThrow(() -> new Exception("등록되지 않은 가맹점입니다."));

        return partnerStore;
    }


}
