package com.mentoring.assignment.membership.domain.partnerstore.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerStoreCategoryRepository extends JpaRepository<PartnerStoreCategory, Long> {
}
