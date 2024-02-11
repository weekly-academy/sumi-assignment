package com.mentoring.assignment.membership.domain.partnerstore.application;

import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.Field;
import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.PartnerCategory;
import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.PartnerCategoryRepository;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerStore;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerStoreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PartnerInitializer {
    private final PartnerCategoryRepository partnerCategoryRepository;
    private final PartnerStoreRepository partnerStoreRepository;


    // save가 있으니까 @Transactional를 사용해야하는데 @PostConstruct와 @Transactional이 같이 있으면 안된다?
    // -> EventListener를 사용..// 다른 여러가지 방법이 있음
    // https://stackoverflow.com/questions/17346679/transactional-on-postconstruct-method
    // https://sorjfkrh5078.tistory.com/311

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("partner onApplicationEvent is called");
        event.getApplicationContext().getBean(PartnerInitializer.class).setup();
    }

    @Transactional
    public void setup() {
        log.info("partner setup is called");
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
}

