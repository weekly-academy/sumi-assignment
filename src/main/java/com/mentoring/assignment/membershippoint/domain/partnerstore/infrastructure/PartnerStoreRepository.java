package com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerStoreRepository extends JpaRepository<PartnerStore, Long> {
}
