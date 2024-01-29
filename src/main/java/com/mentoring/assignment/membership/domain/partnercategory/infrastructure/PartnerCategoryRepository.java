package com.mentoring.assignment.membership.domain.partnercategory.infrastructure;
import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.Field;
import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.PartnerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PartnerCategoryRepository extends JpaRepository<PartnerCategory, Long> {

    Optional<PartnerCategory> findByField(Field field);

}
